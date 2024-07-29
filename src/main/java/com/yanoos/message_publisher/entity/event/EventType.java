package com.yanoos.message_publisher.entity.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "eventType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    List<MapEventEventType> mapEventEventTypes = new ArrayList<>();
}
