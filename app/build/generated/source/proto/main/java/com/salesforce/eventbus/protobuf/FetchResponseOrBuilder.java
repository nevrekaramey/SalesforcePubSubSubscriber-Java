// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pubsub.proto

package com.salesforce.eventbus.protobuf;

public interface FetchResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:eventbus.v1.FetchResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Received events for subscription for client consumption
   * </pre>
   *
   * <code>repeated .eventbus.v1.ConsumerEvent events = 1;</code>
   */
  java.util.List<com.salesforce.eventbus.protobuf.ConsumerEvent> 
      getEventsList();
  /**
   * <pre>
   * Received events for subscription for client consumption
   * </pre>
   *
   * <code>repeated .eventbus.v1.ConsumerEvent events = 1;</code>
   */
  com.salesforce.eventbus.protobuf.ConsumerEvent getEvents(int index);
  /**
   * <pre>
   * Received events for subscription for client consumption
   * </pre>
   *
   * <code>repeated .eventbus.v1.ConsumerEvent events = 1;</code>
   */
  int getEventsCount();
  /**
   * <pre>
   * Received events for subscription for client consumption
   * </pre>
   *
   * <code>repeated .eventbus.v1.ConsumerEvent events = 1;</code>
   */
  java.util.List<? extends com.salesforce.eventbus.protobuf.ConsumerEventOrBuilder> 
      getEventsOrBuilderList();
  /**
   * <pre>
   * Received events for subscription for client consumption
   * </pre>
   *
   * <code>repeated .eventbus.v1.ConsumerEvent events = 1;</code>
   */
  com.salesforce.eventbus.protobuf.ConsumerEventOrBuilder getEventsOrBuilder(
      int index);

  /**
   * <pre>
   * Latest replay ID of a subscription. Enables clients with an updated replay value so that they can keep track
   * of their last consumed replay. Clients will not have to start a subscription at a very old replay in the case where a resubscribe is necessary.
   * </pre>
   *
   * <code>bytes latest_replay_id = 2;</code>
   * @return The latestReplayId.
   */
  com.google.protobuf.ByteString getLatestReplayId();

  /**
   * <pre>
   * RPC ID used to trace errors.
   * </pre>
   *
   * <code>string rpc_id = 3;</code>
   * @return The rpcId.
   */
  java.lang.String getRpcId();
  /**
   * <pre>
   * RPC ID used to trace errors.
   * </pre>
   *
   * <code>string rpc_id = 3;</code>
   * @return The bytes for rpcId.
   */
  com.google.protobuf.ByteString
      getRpcIdBytes();

  /**
   * <pre>
   * Number of remaining events to be delivered to the client for a Subscribe RPC call.
   * </pre>
   *
   * <code>int32 pending_num_requested = 4;</code>
   * @return The pendingNumRequested.
   */
  int getPendingNumRequested();
}
