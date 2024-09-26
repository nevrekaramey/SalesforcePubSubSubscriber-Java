package com.salesforce.pubsub;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.salesforce.eventbus.protobuf.ConsumerEvent;
import com.salesforce.eventbus.protobuf.PubSubGrpc;
import com.salesforce.eventbus.protobuf.FetchRequest;
import com.salesforce.eventbus.protobuf.FetchResponse;
import com.salesforce.eventbus.protobuf.ReplayPreset;
import com.salesforce.eventbus.protobuf.SchemaRequest;
import com.salesforce.eventbus.protobuf.SchemaInfo;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.TypeDescriptors;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import okhttp3.*;

import java.io.IOException;
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

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            // Get OAuth Access Token
            getAccessToken();
            System.out.println("Access Token: " + accessToken);
            System.out.println("Instance URL: " + instanceUrl);

            // Subscribe to events using Salesforce Pub/Sub API
            // subscribeToEvents();

            salesforceEventPipeline();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salesforceEventPipeline() {
        Pipeline pipeline = Pipeline.create();

        String value = "Hello, World!";

        // Use relative or absolute path to input.txt
        pipeline.apply("ReadLines", TextIO.read().from("src/main/resources/input.txt"))
                .apply("ProcessLines", MapElements.into(TypeDescriptors.strings())
                        .via((SerializableFunction<String, String>) input -> "Processed: " + input))
                .apply("WriteLines", TextIO.write().to("output.txt").withoutSharding());

        // Run the pipeline
        pipeline.run().waitUntilFinish();
    }

    private static void getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Build the request body for the OAuth 2.0 password grant
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("username", USERNAME)
                .add("password", PASSWORD)
                .build();

        // Build the request
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(formBody)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // Print the error response for debugging
                System.out.println("Error Response Body: " + response.body().string());
                throw new IOException("Unexpected code " + response);
            }

            // Parse the response
            String responseBody = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

            // Extract Access Token and Instance URL from the response
            accessToken = jsonObject.get("access_token").getAsString();
            instanceUrl = jsonObject.get("instance_url").getAsString();
        }
    }

    private static void subscribeToEvents() {
        // Extract the Salesforce hostname from instance URL
        // String pubSubHostname = instanceUrl.replace("https://", "");
        String pubSubHostname = "api.pubsub.salesforce.com";

        // Create a gRPC channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(pubSubHostname, 443)
                .useTransportSecurity()  // Use TLS
                .build();

        // Create a stub for the Pub/Sub API
        stub = PubSubGrpc.newStub(channel);

        // Metadata for authentication (access token and tenant ID)
        Metadata headers = new Metadata();
        Metadata.Key<String> accessTokenKey = Metadata.Key.of("accesstoken", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> instanceUrlKey = Metadata.Key.of("instanceurl", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> tenantIdKey = Metadata.Key.of("tenantid", Metadata.ASCII_STRING_MARSHALLER);

        headers.put(accessTokenKey, accessToken);
        headers.put(instanceUrlKey, instanceUrl);
        headers.put(tenantIdKey, "00DG1000002eahx");  // Replace with your Salesforce tenant ID

        // Attach the metadata to the stub
        stub = MetadataUtils.attachHeaders(stub, headers);

        // Create the request for subscribing to events
        FetchRequest fetchRequest = FetchRequest.newBuilder()
                .setTopicName(TOPIC_NAME)
                .setNumRequested(NUM_EVENTS)
                .setReplayPreset(ReplayPreset.LATEST)
                .build();

        // Handle the subscription response
        StreamObserver<FetchResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(FetchResponse fetchResponse) {
                for (ConsumerEvent event : fetchResponse.getEventsList()) {
                    System.out.println("Received event: " + event.getEvent());
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Failed to fetch schema for schema ID: " + schemaId);
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error during subscription: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Subscription completed.");
            }
        };

        // Start the subscription by sending the FetchRequest
        stub.subscribe(responseObserver).onNext(fetchRequest);

        // Add a shutdown hook to properly close the application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gracefully...");
            latch.countDown();  // Release the latch to stop the application
        }));

        try {
            latch.await();  // Keep the main thread alive until the latch is counted down
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
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
