package com.yanoos.message_publisher.entity.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventTypeId;

    @Column(name = "event_type", nullable = false)
    private String eventType;
}
