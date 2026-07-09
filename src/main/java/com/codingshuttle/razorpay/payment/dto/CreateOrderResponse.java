package com.codingshuttle.razorpay.payment.dto;


import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record CreateOrderResponse(
        UUID id,
        UUID merchantId,
        String receipt, // order ID ( which is known to a merchant) [  merchant ref number ]
        Money amount,
        OrderStatus orderStatus,
        Integer attempts,
        Map<String, Object> notes,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {
}
