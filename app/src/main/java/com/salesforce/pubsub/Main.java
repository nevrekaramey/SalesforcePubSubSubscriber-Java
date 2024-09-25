package com.salesforce.pubsub;

import com.salesforce.eventbus.protobuf.FetchRequest;
import com.salesforce.eventbus.protobuf.FetchResponse;
import com.salesforce.eventbus.protobuf.ReplayPreset;
import com.salesforce.eventbus.protobuf.PubSubGrpc;
import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String clientId = "your-client-id";
        String clientSecret = "your-client-secret";
        String username = "your-salesforce-username";
        String password = "your-salesforce-password";
        String authUrl = "https://login.salesforce.com/services/oauth2/token";

        String accessToken = getAccessToken(clientId, clientSecret, username, password, authUrl);
        String instanceUrl = "your-instance-url";
        String tenantId = "your-tenant-id";

        subscribeToEvents(accessToken, instanceUrl, tenantId);
    }

    public static String getAccessToken(String clientId, String clientSecret, String username, String password, String authUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(authUrl);

        StringEntity params = new StringEntity(
                "grant_type=password&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&username=" + username +
                "&password=" + password);

        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setEntity(params);

        String response = EntityUtils.toString(httpClient.execute(post).getEntity());
        JSONObject json = new JSONObject(response);
        return json.getString("access_token");
    }

    public static void subscribeToEvents(String accessToken, String instanceUrl, String tenantId) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(instanceUrl)
                .useTransportSecurity()
                .build();

        Metadata headers = new Metadata();
        Metadata.Key<String> AUTH_TOKEN_KEY = Metadata.Key.of("accesstoken", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> TENANT_ID_KEY = Metadata.Key.of("tenantid", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> INSTANCE_URL_KEY = Metadata.Key.of("instanceurl", Metadata.ASCII_STRING_MARSHALLER);

        headers.put(AUTH_TOKEN_KEY, accessToken);
        headers.put(TENANT_ID_KEY, tenantId);
        headers.put(INSTANCE_URL_KEY, instanceUrl);

        PubSubGrpc.PubSubStub pubSubStub = PubSubGrpc.newStub(channel);
        pubSubStub = MetadataUtils.attachHeaders(pubSubStub, headers);

        FetchRequest request = FetchRequest.newBuilder()
                .setTopicName("Your_Topic_Name")
                .setNumRequested(10)
                .setReplayPreset(ReplayPreset.LATEST)
                .build();

        pubSubStub.subscribe(new StreamObserver<FetchResponse>() {
            @Override
            public void onNext(FetchResponse response) {
                response.getEventsList().forEach(event -> {
                    System.out.println("Received event: " + event.getEvent().getPayload());
                });
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed");
            }
        }).onNext(request);
    }
}
