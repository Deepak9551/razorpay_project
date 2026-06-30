package com.codingshuttle.razorpay.payment.entity;

import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.PaymentMethod;
import com.codingshuttle.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    // many payments belong to one order record because if the order is failed then for same order we can create (another) multiple payments
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "order_record_id", nullable = false)
    private OrderRecord orderRecord;

    // idempotency key is used to prevent duplicate payments ( so that same request can be processed multiple times)
    private String idempotencyKey;

    // no FK because of cross service boundary
    private UUID merchantId;
    @Embedded
    private Money amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    // json meta data of payment method
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> methodDetails = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    
    private String bankReference;
    private String errorCode;
    private String errorDetails;

    // time status of payment
    private LocalDateTime authorizedAt;
    private LocalDateTime capturedAt;
    private LocalDateTime settledAt;
    private LocalDateTime failedAt;
    private LocalDateTime refundedAt;
//    private LocalDateTime expiredAt;
}
