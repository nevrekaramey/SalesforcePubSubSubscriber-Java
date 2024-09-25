// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pubsub.proto

package com.salesforce.eventbus.protobuf;

/**
 * <pre>
 * Supported error codes
 * </pre>
 *
 * Protobuf enum {@code eventbus.v1.ErrorCode}
 */
public enum ErrorCode
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>UNKNOWN = 0;</code>
   */
  UNKNOWN(0),
  /**
   * <code>PUBLISH = 1;</code>
   */
  PUBLISH(1),
  /**
   * <pre>
   * ErrorCode for unrecoverable commit errors.
   * </pre>
   *
   * <code>COMMIT = 2;</code>
   */
  COMMIT(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>UNKNOWN = 0;</code>
   */
  public static final int UNKNOWN_VALUE = 0;
  /**
   * <code>PUBLISH = 1;</code>
   */
  public static final int PUBLISH_VALUE = 1;
  /**
   * <pre>
   * ErrorCode for unrecoverable commit errors.
   * </pre>
   *
   * <code>COMMIT = 2;</code>
   */
  public static final int COMMIT_VALUE = 2;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static ErrorCode valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static ErrorCode forNumber(int value) {
    switch (value) {
      case 0: return UNKNOWN;
      case 1: return PUBLISH;
      case 2: return COMMIT;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<ErrorCode>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      ErrorCode> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ErrorCode>() {
          public ErrorCode findValueByNumber(int number) {
            return ErrorCode.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.salesforce.eventbus.protobuf.PubSubProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final ErrorCode[] VALUES = values();

  public static ErrorCode valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private ErrorCode(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:eventbus.v1.ErrorCode)
}

