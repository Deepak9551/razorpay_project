package com.codingshuttle.razorpay.payment.dto.response;


import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
@Builder
public record OrderResponse(
        UUID id,
        UUID merchantId,
        String receipt, // order ID ( which is known to a merchant) [  merchant ref number ]
        Money amount,
        OrderStatus status,
        Integer attempts,
        Map<String, Object> notes,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {
}
