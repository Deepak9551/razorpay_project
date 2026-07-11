package com.codingshuttle.razorpay.operations.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "dlq_event")
public class DlqEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

    @Column(nullable = false)
    private UUID merchantId;

    // if webhook event is not processed/ fail then it will be again moved to dlq
    // only movedAt time is updated
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "webhook_event_id", nullable = false)
    private WebhookEvent webhookEventId;

    @Column(length = 1000)
    private String finalError;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> payload;

    private LocalDateTime movedAt;

    private  LocalDateTime replayedAt;
}
