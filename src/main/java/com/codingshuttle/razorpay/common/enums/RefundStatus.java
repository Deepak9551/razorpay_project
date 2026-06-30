package com.codingshuttle.razorpay.common.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public enum RefundStatus {

    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
}
