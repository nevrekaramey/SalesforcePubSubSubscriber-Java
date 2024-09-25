package com.salesforce.pubsub;

import com.salesforce.eventbus.protobuf.FetchRequest;
import com.salesforce.eventbus.protobuf.ReplayPreset;

// simple class to test that the generated proto classes are working, can be removed.
public class TestProto {
    public static void main(String[] args) {
        FetchRequest request = FetchRequest.newBuilder()
            .setTopicName("TestTopic")
            .setReplayPreset(ReplayPreset.LATEST)
            .setNumRequested(10)
            .build();
        System.out.println("FetchRequest created: " + request);
    }
}
