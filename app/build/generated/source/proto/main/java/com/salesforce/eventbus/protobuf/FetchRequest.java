// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pubsub.proto

package com.salesforce.eventbus.protobuf;

/**
 * <pre>
 * Request for the Subscribe streaming RPC method. This request is used to:
 * 1. Establish the initial subscribe stream.
 * 2. Request more events from the subscription stream.
 * Flow Control is handled by the subscriber via num_requested.
 * A client can specify a starting point for the subscription with replay_preset and replay_id combinations.
 * If no replay_preset is specified, the subscription starts at LATEST (tip of the stream).
 * replay_preset and replay_id values are only consumed as part of the first FetchRequest. If
 * a client needs to start at another point in the stream, it must start a new subscription.
 * </pre>
 *
 * Protobuf type {@code eventbus.v1.FetchRequest}
 */
public final class FetchRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:eventbus.v1.FetchRequest)
    FetchRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use FetchRequest.newBuilder() to construct.
  private FetchRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FetchRequest() {
    topicName_ = "";
    replayPreset_ = 0;
    replayId_ = com.google.protobuf.ByteString.EMPTY;
    authRefresh_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new FetchRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private FetchRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            topicName_ = s;
            break;
          }
          case 16: {
            int rawValue = input.readEnum();

            replayPreset_ = rawValue;
            break;
          }
          case 26: {

            replayId_ = input.readBytes();
            break;
          }
          case 32: {

            numRequested_ = input.readInt32();
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            authRefresh_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.salesforce.eventbus.protobuf.PubSubProto.internal_static_eventbus_v1_FetchRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.salesforce.eventbus.protobuf.PubSubProto.internal_static_eventbus_v1_FetchRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.salesforce.eventbus.protobuf.FetchRequest.class, com.salesforce.eventbus.protobuf.FetchRequest.Builder.class);
  }

  public static final int TOPIC_NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object topicName_;
  /**
   * <pre>
   * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
   * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
   * </pre>
   *
   * <code>string topic_name = 1;</code>
   * @return The topicName.
   */
  @java.lang.Override
  public java.lang.String getTopicName() {
    java.lang.Object ref = topicName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      topicName_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
   * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
   * </pre>
   *
   * <code>string topic_name = 1;</code>
   * @return The bytes for topicName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getTopicNameBytes() {
    java.lang.Object ref = topicName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      topicName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int REPLAY_PRESET_FIELD_NUMBER = 2;
  private int replayPreset_;
  /**
   * <pre>
   * Subscription starting point. This is consumed only as part of the first FetchRequest
   * when the subscription is set up.
   * </pre>
   *
   * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
   * @return The enum numeric value on the wire for replayPreset.
   */
  @java.lang.Override public int getReplayPresetValue() {
    return replayPreset_;
  }
  /**
   * <pre>
   * Subscription starting point. This is consumed only as part of the first FetchRequest
   * when the subscription is set up.
   * </pre>
   *
   * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
   * @return The replayPreset.
   */
  @java.lang.Override public com.salesforce.eventbus.protobuf.ReplayPreset getReplayPreset() {
    @SuppressWarnings("deprecation")
    com.salesforce.eventbus.protobuf.ReplayPreset result = com.salesforce.eventbus.protobuf.ReplayPreset.valueOf(replayPreset_);
    return result == null ? com.salesforce.eventbus.protobuf.ReplayPreset.UNRECOGNIZED : result;
  }

  public static final int REPLAY_ID_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString replayId_;
  /**
   * <pre>
   * If replay_preset of CUSTOM is selected, specify the subscription point to start after.
   * This is consumed only as part of the first FetchRequest when the subscription is set up.
   * </pre>
   *
   * <code>bytes replay_id = 3;</code>
   * @return The replayId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getReplayId() {
    return replayId_;
  }

  public static final int NUM_REQUESTED_FIELD_NUMBER = 4;
  private int numRequested_;
  /**
   * <pre>
   * Number of events a client is ready to accept. Each subsequent FetchRequest informs the server
   * of additional processing capacity available on the client side. There is no guarantee of equal number of
   * FetchResponse messages to be sent back. There is not necessarily a correspondence between
   * number of requested events in FetchRequest and the number of events returned in subsequent
   * FetchResponses.
   * </pre>
   *
   * <code>int32 num_requested = 4;</code>
   * @return The numRequested.
   */
  @java.lang.Override
  public int getNumRequested() {
    return numRequested_;
  }

  public static final int AUTH_REFRESH_FIELD_NUMBER = 5;
  private volatile java.lang.Object authRefresh_;
  /**
   * <pre>
   * For internal Salesforce use only.
   * </pre>
   *
   * <code>string auth_refresh = 5;</code>
   * @return The authRefresh.
   */
  @java.lang.Override
  public java.lang.String getAuthRefresh() {
    java.lang.Object ref = authRefresh_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      authRefresh_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * For internal Salesforce use only.
   * </pre>
   *
   * <code>string auth_refresh = 5;</code>
   * @return The bytes for authRefresh.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAuthRefreshBytes() {
    java.lang.Object ref = authRefresh_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      authRefresh_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(topicName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, topicName_);
    }
    if (replayPreset_ != com.salesforce.eventbus.protobuf.ReplayPreset.LATEST.getNumber()) {
      output.writeEnum(2, replayPreset_);
    }
    if (!replayId_.isEmpty()) {
      output.writeBytes(3, replayId_);
    }
    if (numRequested_ != 0) {
      output.writeInt32(4, numRequested_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(authRefresh_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, authRefresh_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(topicName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, topicName_);
    }
    if (replayPreset_ != com.salesforce.eventbus.protobuf.ReplayPreset.LATEST.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, replayPreset_);
    }
    if (!replayId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, replayId_);
    }
    if (numRequested_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, numRequested_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(authRefresh_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, authRefresh_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.salesforce.eventbus.protobuf.FetchRequest)) {
      return super.equals(obj);
    }
    com.salesforce.eventbus.protobuf.FetchRequest other = (com.salesforce.eventbus.protobuf.FetchRequest) obj;

    if (!getTopicName()
        .equals(other.getTopicName())) return false;
    if (replayPreset_ != other.replayPreset_) return false;
    if (!getReplayId()
        .equals(other.getReplayId())) return false;
    if (getNumRequested()
        != other.getNumRequested()) return false;
    if (!getAuthRefresh()
        .equals(other.getAuthRefresh())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TOPIC_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getTopicName().hashCode();
    hash = (37 * hash) + REPLAY_PRESET_FIELD_NUMBER;
    hash = (53 * hash) + replayPreset_;
    hash = (37 * hash) + REPLAY_ID_FIELD_NUMBER;
    hash = (53 * hash) + getReplayId().hashCode();
    hash = (37 * hash) + NUM_REQUESTED_FIELD_NUMBER;
    hash = (53 * hash) + getNumRequested();
    hash = (37 * hash) + AUTH_REFRESH_FIELD_NUMBER;
    hash = (53 * hash) + getAuthRefresh().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.salesforce.eventbus.protobuf.FetchRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.salesforce.eventbus.protobuf.FetchRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Request for the Subscribe streaming RPC method. This request is used to:
   * 1. Establish the initial subscribe stream.
   * 2. Request more events from the subscription stream.
   * Flow Control is handled by the subscriber via num_requested.
   * A client can specify a starting point for the subscription with replay_preset and replay_id combinations.
   * If no replay_preset is specified, the subscription starts at LATEST (tip of the stream).
   * replay_preset and replay_id values are only consumed as part of the first FetchRequest. If
   * a client needs to start at another point in the stream, it must start a new subscription.
   * </pre>
   *
   * Protobuf type {@code eventbus.v1.FetchRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:eventbus.v1.FetchRequest)
      com.salesforce.eventbus.protobuf.FetchRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.salesforce.eventbus.protobuf.PubSubProto.internal_static_eventbus_v1_FetchRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.salesforce.eventbus.protobuf.PubSubProto.internal_static_eventbus_v1_FetchRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.salesforce.eventbus.protobuf.FetchRequest.class, com.salesforce.eventbus.protobuf.FetchRequest.Builder.class);
    }

    // Construct using com.salesforce.eventbus.protobuf.FetchRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      topicName_ = "";

      replayPreset_ = 0;

      replayId_ = com.google.protobuf.ByteString.EMPTY;

      numRequested_ = 0;

      authRefresh_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.salesforce.eventbus.protobuf.PubSubProto.internal_static_eventbus_v1_FetchRequest_descriptor;
    }

    @java.lang.Override
    public com.salesforce.eventbus.protobuf.FetchRequest getDefaultInstanceForType() {
      return com.salesforce.eventbus.protobuf.FetchRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.salesforce.eventbus.protobuf.FetchRequest build() {
      com.salesforce.eventbus.protobuf.FetchRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.salesforce.eventbus.protobuf.FetchRequest buildPartial() {
      com.salesforce.eventbus.protobuf.FetchRequest result = new com.salesforce.eventbus.protobuf.FetchRequest(this);
      result.topicName_ = topicName_;
      result.replayPreset_ = replayPreset_;
      result.replayId_ = replayId_;
      result.numRequested_ = numRequested_;
      result.authRefresh_ = authRefresh_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.salesforce.eventbus.protobuf.FetchRequest) {
        return mergeFrom((com.salesforce.eventbus.protobuf.FetchRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.salesforce.eventbus.protobuf.FetchRequest other) {
      if (other == com.salesforce.eventbus.protobuf.FetchRequest.getDefaultInstance()) return this;
      if (!other.getTopicName().isEmpty()) {
        topicName_ = other.topicName_;
        onChanged();
      }
      if (other.replayPreset_ != 0) {
        setReplayPresetValue(other.getReplayPresetValue());
      }
      if (other.getReplayId() != com.google.protobuf.ByteString.EMPTY) {
        setReplayId(other.getReplayId());
      }
      if (other.getNumRequested() != 0) {
        setNumRequested(other.getNumRequested());
      }
      if (!other.getAuthRefresh().isEmpty()) {
        authRefresh_ = other.authRefresh_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.salesforce.eventbus.protobuf.FetchRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.salesforce.eventbus.protobuf.FetchRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object topicName_ = "";
    /**
     * <pre>
     * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
     * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
     * </pre>
     *
     * <code>string topic_name = 1;</code>
     * @return The topicName.
     */
    public java.lang.String getTopicName() {
      java.lang.Object ref = topicName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        topicName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
     * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
     * </pre>
     *
     * <code>string topic_name = 1;</code>
     * @return The bytes for topicName.
     */
    public com.google.protobuf.ByteString
        getTopicNameBytes() {
      java.lang.Object ref = topicName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        topicName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
     * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
     * </pre>
     *
     * <code>string topic_name = 1;</code>
     * @param value The topicName to set.
     * @return This builder for chaining.
     */
    public Builder setTopicName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      topicName_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
     * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
     * </pre>
     *
     * <code>string topic_name = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTopicName() {
      
      topicName_ = getDefaultInstance().getTopicName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Identifies a topic for subscription in the very first FetchRequest of the stream. The topic cannot change
     * in subsequent FetchRequests within the same subscribe stream, but can be omitted for efficiency.
     * </pre>
     *
     * <code>string topic_name = 1;</code>
     * @param value The bytes for topicName to set.
     * @return This builder for chaining.
     */
    public Builder setTopicNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      topicName_ = value;
      onChanged();
      return this;
    }

    private int replayPreset_ = 0;
    /**
     * <pre>
     * Subscription starting point. This is consumed only as part of the first FetchRequest
     * when the subscription is set up.
     * </pre>
     *
     * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
     * @return The enum numeric value on the wire for replayPreset.
     */
    @java.lang.Override public int getReplayPresetValue() {
      return replayPreset_;
    }
    /**
     * <pre>
     * Subscription starting point. This is consumed only as part of the first FetchRequest
     * when the subscription is set up.
     * </pre>
     *
     * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
     * @param value The enum numeric value on the wire for replayPreset to set.
     * @return This builder for chaining.
     */
    public Builder setReplayPresetValue(int value) {
      
      replayPreset_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Subscription starting point. This is consumed only as part of the first FetchRequest
     * when the subscription is set up.
     * </pre>
     *
     * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
     * @return The replayPreset.
     */
    @java.lang.Override
    public com.salesforce.eventbus.protobuf.ReplayPreset getReplayPreset() {
      @SuppressWarnings("deprecation")
      com.salesforce.eventbus.protobuf.ReplayPreset result = com.salesforce.eventbus.protobuf.ReplayPreset.valueOf(replayPreset_);
      return result == null ? com.salesforce.eventbus.protobuf.ReplayPreset.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     * Subscription starting point. This is consumed only as part of the first FetchRequest
     * when the subscription is set up.
     * </pre>
     *
     * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
     * @param value The replayPreset to set.
     * @return This builder for chaining.
     */
    public Builder setReplayPreset(com.salesforce.eventbus.protobuf.ReplayPreset value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      replayPreset_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Subscription starting point. This is consumed only as part of the first FetchRequest
     * when the subscription is set up.
     * </pre>
     *
     * <code>.eventbus.v1.ReplayPreset replay_preset = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearReplayPreset() {
      
      replayPreset_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString replayId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * If replay_preset of CUSTOM is selected, specify the subscription point to start after.
     * This is consumed only as part of the first FetchRequest when the subscription is set up.
     * </pre>
     *
     * <code>bytes replay_id = 3;</code>
     * @return The replayId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getReplayId() {
      return replayId_;
    }
    /**
     * <pre>
     * If replay_preset of CUSTOM is selected, specify the subscription point to start after.
     * This is consumed only as part of the first FetchRequest when the subscription is set up.
     * </pre>
     *
     * <code>bytes replay_id = 3;</code>
     * @param value The replayId to set.
     * @return This builder for chaining.
     */
    public Builder setReplayId(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      replayId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * If replay_preset of CUSTOM is selected, specify the subscription point to start after.
     * This is consumed only as part of the first FetchRequest when the subscription is set up.
     * </pre>
     *
     * <code>bytes replay_id = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearReplayId() {
      
      replayId_ = getDefaultInstance().getReplayId();
      onChanged();
      return this;
    }

    private int numRequested_ ;
    /**
     * <pre>
     * Number of events a client is ready to accept. Each subsequent FetchRequest informs the server
     * of additional processing capacity available on the client side. There is no guarantee of equal number of
     * FetchResponse messages to be sent back. There is not necessarily a correspondence between
     * number of requested events in FetchRequest and the number of events returned in subsequent
     * FetchResponses.
     * </pre>
     *
     * <code>int32 num_requested = 4;</code>
     * @return The numRequested.
     */
    @java.lang.Override
    public int getNumRequested() {
      return numRequested_;
    }
    /**
     * <pre>
     * Number of events a client is ready to accept. Each subsequent FetchRequest informs the server
     * of additional processing capacity available on the client side. There is no guarantee of equal number of
     * FetchResponse messages to be sent back. There is not necessarily a correspondence between
     * number of requested events in FetchRequest and the number of events returned in subsequent
     * FetchResponses.
     * </pre>
     *
     * <code>int32 num_requested = 4;</code>
     * @param value The numRequested to set.
     * @return This builder for chaining.
     */
    public Builder setNumRequested(int value) {
      
      numRequested_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Number of events a client is ready to accept. Each subsequent FetchRequest informs the server
     * of additional processing capacity available on the client side. There is no guarantee of equal number of
     * FetchResponse messages to be sent back. There is not necessarily a correspondence between
     * number of requested events in FetchRequest and the number of events returned in subsequent
     * FetchResponses.
     * </pre>
     *
     * <code>int32 num_requested = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearNumRequested() {
      
      numRequested_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object authRefresh_ = "";
    /**
     * <pre>
     * For internal Salesforce use only.
     * </pre>
     *
     * <code>string auth_refresh = 5;</code>
     * @return The authRefresh.
     */
    public java.lang.String getAuthRefresh() {
      java.lang.Object ref = authRefresh_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        authRefresh_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * For internal Salesforce use only.
     * </pre>
     *
     * <code>string auth_refresh = 5;</code>
     * @return The bytes for authRefresh.
     */
    public com.google.protobuf.ByteString
        getAuthRefreshBytes() {
      java.lang.Object ref = authRefresh_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        authRefresh_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * For internal Salesforce use only.
     * </pre>
     *
     * <code>string auth_refresh = 5;</code>
     * @param value The authRefresh to set.
     * @return This builder for chaining.
     */
    public Builder setAuthRefresh(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      authRefresh_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * For internal Salesforce use only.
     * </pre>
     *
     * <code>string auth_refresh = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearAuthRefresh() {
      
      authRefresh_ = getDefaultInstance().getAuthRefresh();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * For internal Salesforce use only.
     * </pre>
     *
     * <code>string auth_refresh = 5;</code>
     * @param value The bytes for authRefresh to set.
     * @return This builder for chaining.
     */
    public Builder setAuthRefreshBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      authRefresh_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:eventbus.v1.FetchRequest)
  }

  // @@protoc_insertion_point(class_scope:eventbus.v1.FetchRequest)
  private static final com.salesforce.eventbus.protobuf.FetchRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.salesforce.eventbus.protobuf.FetchRequest();
  }

  public static com.salesforce.eventbus.protobuf.FetchRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FetchRequest>
      PARSER = new com.google.protobuf.AbstractParser<FetchRequest>() {
    @java.lang.Override
    public FetchRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new FetchRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<FetchRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<FetchRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.salesforce.eventbus.protobuf.FetchRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

