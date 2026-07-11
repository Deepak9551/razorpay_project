package com.codingshuttle.razorpay.payment.dto.request;

import com.codingshuttle.razorpay.common.entity.Money;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateOrderRecord (
        @NotNull(message = "amount is required")
    Money amount,
    @Size(min = 1, max = 255, message = "receipt must be between 1 and 255 characters")
    String receipt, // order ID ( which is known to a merchant)
    Map<String, Object> notes, // json meta data ( merchant can add any key value pair information)
    LocalDateTime expiredAt
){}
