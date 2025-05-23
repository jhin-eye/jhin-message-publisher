package com.yanoos.message_publisher.entity.event;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "event")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @Column(name = "parent_event_id", nullable = true) // nullable을 true로 설정하여 parent가 없을 때도 처리 가능
    private Long parentEventId;

    @Column(name = "event_data", columnDefinition = "json", nullable = false)
    private String eventData;

    @Column(name = "published", nullable = false)
    private Boolean published;

    @Column(name = "event_type", nullable = false)
    private String eventType;
    @Column(name="created_at",nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));;
    @Column(name="try_count", nullable = false)
    private Long tryCount;
    @Column(name="published_at", nullable = true)
    private ZonedDateTime publishedAt;

    public void done() {
        this.published =true;
        this.publishedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void addTryCount(){
        this.tryCount++;
    }
}
