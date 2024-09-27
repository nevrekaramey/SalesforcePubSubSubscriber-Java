package com.salesforce.pubsub;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.salesforce.eventbus.protobuf.ConsumerEvent;
import com.salesforce.eventbus.protobuf.FetchRequest;
import com.salesforce.eventbus.protobuf.FetchResponse;
import com.salesforce.eventbus.protobuf.PubSubGrpc;
import com.salesforce.eventbus.protobuf.ReplayPreset;
import com.salesforce.eventbus.protobuf.SchemaInfo;
import com.salesforce.eventbus.protobuf.SchemaRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import okhttp3.*;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.transforms.Create;  // Import Create
import org.apache.beam.sdk.values.TypeDescriptors;  // Import TypeDescriptors

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    // Declare schemaCache at the class level
    private static final Map<String, org.apache.avro.Schema> schemaCache = new ConcurrentHashMap<>();
    private static PubSubGrpc.PubSubStub stub;
    private static final String LOGIN_URL = "https://test.salesforce.com/services/oauth2/token";
    private static final String CLIENT_ID = "";  // Your connected app client ID
    private static final String CLIENT_SECRET = "";  // Your connected app client secret
    private static final String USERNAME = "";  // Your Salesforce sandbox username
    private static final String PASSWORD = "";  // Your Salesforce sandbox password (without security token since it's disabled)
    private static final String TOPIC_NAME = "/data/LeadChangeEvent";  // The topic name you want to subscribe to
    private static final int NUM_EVENTS = 10;  // Number of events to request
    private static String accessToken;
    private static String instanceUrl;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting Salesforce Pub/Sub Beam pipeline...");
        // Create a Beam pipeline
        Pipeline pipeline = Pipeline.create();

        // Authenticate and get access token
        getAccessToken();
        System.out.println("Access token: " + accessToken);
        System.out.println("Instance URL: " + instanceUrl);

        // Create a PCollection with a dummy input to trigger the pipeline
        PCollection<String> triggerInput = pipeline.apply("CreateDummyInput", Create.of("start_trigger"));

        // Fetch events from Salesforce Pub/Sub API and process them in Apache Beam
        PCollection<String> salesforceEvents = triggerInput
                .apply("FetchSalesforceEvents", ParDo.of(new FetchSalesforceEvents()));

        // Process the events (you can apply any necessary transformations here)
        salesforceEvents.apply("ProcessEvents", ParDo.of(new DoFn<String, String>() {
            @ProcessElement
            public void processElement(ProcessContext c) {
                String event = c.element();
                c.output("Processed Event: " + event);
            }
        }))
                // Output results (e.g., write to a file or another system)
                .apply("WriteToOutput", TextIO.write().to("salesforce_output.txt").withoutSharding());

        // Run the pipeline
        pipeline.run();

        // Keep the main thread alive to continuously receive events
        synchronized (Main.class) {
            Main.class.wait();  // Keep the program alive indefinitely
        }
    }

    // FetchSalesforceEvents is a DoFn that will fetch events from Salesforce and return them as Strings.
    static class FetchSalesforceEvents extends DoFn<String, String> {
        @ProcessElement
        public void processElement(ProcessContext context) {
            System.out.println("Fetching Salesforce events...");
            CountDownLatch latch = new CountDownLatch(1);
            subscribeToEvents(latch, context);
            try {
                latch.await(); // Ensure we wait for events to be received
            } catch (InterruptedException e) {
                System.err.println("Error while waiting for events: " + e.getMessage());
            }
        }
    }

    // Salesforce Pub/Sub subscription logic
    private static void subscribeToEvents(CountDownLatch latch, DoFn<String, String>.ProcessContext context) {
        String pubSubHostname = "api.pubsub.salesforce.com";
        ManagedChannel channel = ManagedChannelBuilder.forAddress(pubSubHostname, 443).useTransportSecurity().build();
        stub = PubSubGrpc.newStub(channel);

        Metadata headers = new Metadata();
        Metadata.Key<String> accessTokenKey = Metadata.Key.of("accesstoken", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> instanceUrlKey = Metadata.Key.of("instanceurl", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> tenantIdKey = Metadata.Key.of("tenantid", Metadata.ASCII_STRING_MARSHALLER);
        headers.put(accessTokenKey, accessToken);
        headers.put(instanceUrlKey, instanceUrl);
        headers.put(tenantIdKey, "00DG1000002eahx");  // Replace with your Salesforce tenant ID
        stub = MetadataUtils.attachHeaders(stub, headers);

        FetchRequest fetchRequest = FetchRequest.newBuilder()
                .setTopicName(TOPIC_NAME)
                .setNumRequested(NUM_EVENTS)
                .setReplayPreset(ReplayPreset.LATEST)
                .build();

        System.out.println("Subscribing to Salesforce events...");

        stub.subscribe(new StreamObserver<>() {
            @Override
            public void onNext(FetchResponse response) {
                for (ConsumerEvent event : response.getEventsList()) {
                    String schemaId = event.getEvent().getSchemaId();
                    System.out.println("Received event with schema ID: " + schemaId);

                    // Use AtomicReference to hold schema
                    AtomicReference<org.apache.avro.Schema> schemaRef = new AtomicReference<>(schemaCache.get(schemaId));

                    if (schemaRef.get() == null) {
                        // Fetch the schema from Salesforce dynamically
                        schemaRef.set(fetchSchema(schemaId));  // Now schemaRef is modifiable

                        if (schemaRef.get() != null) {
                            // Cache the schema for future use
                            schemaCache.put(schemaId, schemaRef.get());
                        }
                    }

                    if (schemaRef.get() != null) {
                        // Deserialize the event payload
                        byte[] avroData = event.getEvent().getPayload().toByteArray();
                        try {
                            GenericRecord record = deserializeAvro(avroData, schemaRef.get());
                            System.out.println("Deserialized Event Data: " + record);
                            System.out.println("Received event before passing to beam pipeline: ");
                            context.output(record.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Failed to fetch schema for schema ID: " + schemaId);
                    }

                    // String eventData = new String(event.getEvent().getPayload().toByteArray());
                    // System.out.println("Received event before passing to beam pipeline: " + eventData);
                    // context.output(eventData);  // Output event data to Beam pipeline
                }
                latch.countDown(); // Signal that the event has been received
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error receiving events: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                latch.countDown();
                System.out.println("Event subscription completed.");
            }
        }).onNext(fetchRequest);
    }

    // Function to get the Salesforce access token
    private static void getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("username", USERNAME)
                .add("password", PASSWORD)
                .build();
        Request request = new Request.Builder().url(LOGIN_URL).post(formBody).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();
            instanceUrl = jsonObject.get("instance_url").getAsString();
        }
    }

    // Modify fetchSchema to no longer need stub as a parameter, since it's now a class-level field
    private static org.apache.avro.Schema fetchSchema(String schemaId) {
        SchemaRequest schemaRequest = SchemaRequest.newBuilder()
                .setSchemaId(schemaId)
                .build();

        // Get the schema from Salesforce Pub/Sub API
        final org.apache.avro.Schema[] schema = {null};
        CountDownLatch latch = new CountDownLatch(1);

        stub.getSchema(schemaRequest, new StreamObserver<SchemaInfo>() {
            @Override
            public void onNext(SchemaInfo schemaInfo) {
                System.out.println("Received schema: " + schemaInfo.getSchemaJson());
                schema[0] = new org.apache.avro.Schema.Parser().parse(schemaInfo.getSchemaJson());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error fetching schema: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        try {
            latch.await();  // Wait for schema fetching to complete
        } catch (InterruptedException e) {
            System.err.println("Error waiting for schema fetch: " + e.getMessage());
        }

        return schema[0];
    }

    private static GenericRecord deserializeAvro(byte[] avroData, org.apache.avro.Schema schema) throws IOException {
        DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(schema);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(avroData);
        Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
        return datumReader.read(null, decoder);
    }
}
