package com.codingshuttle.razorpay.common.enums;

public enum PaymentStatus {
    CREATED,
    AUTHORIZING,
    AUTHORIZED,
    CAPTURING,
    CAPTURED,
    FAILED,
    AUTHORIZED_FAILED,
    CAPTURED_FAILED,
    AUTH_EXPIRED
}
