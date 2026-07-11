package com.codingshuttle.razorpay.payment.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.RefundStatus;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    // to identity which refund belongs to which merchant
    // many refund belong to single merchant
    // @ManyToOne - no we only store ID because no FK because of cross service boundary

    @Column(name = "merchant_id", nullable = false)
    private UUID merchant;
    @Embedded
    private Money amount;
    @Enumerated(EnumType.STRING)
    private RefundStatus status = RefundStatus.PENDING;

    // many refund belong to single payment
    @ManyToOne(fetch = FetchType.LAZY,optional = false)  // 30 % refund - 70 % by another refund
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "bank_reference", nullable = false,length = 100)
    private String bankReference;

    @Column(name = "error_code", length = 100)
    private String errorCode;
    @Column(name = "error_details", length = 100)
    private String errorDetails;

    private LocalDateTime processedAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> notes; // why this payment is refund

}
