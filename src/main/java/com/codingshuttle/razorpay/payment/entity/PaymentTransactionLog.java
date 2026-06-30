package com.codingshuttle.razorpay.payment.entity;

import com.codingshuttle.razorpay.common.enums.PaymentActor;
import com.codingshuttle.razorpay.common.enums.PaymentEvent;
import com.codingshuttle.razorpay.common.enums.PaymentMethod;
import com.codingshuttle.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionLog {
//
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private PaymentStatus fromStatus;
    @Enumerated(EnumType.STRING)
    private PaymentEvent event;
    @Enumerated(EnumType.STRING)
    private PaymentStatus toStatus;

    @Enumerated(EnumType.STRING)
    private PaymentActor actor;
    private LocalDateTime occurredAt;
}
