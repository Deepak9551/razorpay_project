package com.codingshuttle.razorpay.operations.entity;

import com.codingshuttle.razorpay.common.enums.WebhookEventStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

// for log purpose store webhook events in database
@Entity
public class WebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID merchantId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false,columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> payload;

    @Enumerated(EnumType.STRING)
    private WebhookEventStatus status;

    @Column(nullable = false)
    private String targetUrl;

    @Column(nullable = false)
    private String signature;

    private Integer attempts;

    private LocalDateTime nextRetryAt;

    private LocalDateTime lastAttemptAt;

    private Integer lastResponseCode;

    private String lastResponseBody;

    private LocalDateTime deliveredAt;
}
