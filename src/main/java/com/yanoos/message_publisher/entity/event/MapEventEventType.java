package com.yanoos.message_publisher.entity.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "map_event_event_type")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapEventEventType {

    @EmbeddedId
    private MapEventEventTypeId id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @MapsId("eventTypeId")
    @JoinColumn(name = "event_type_id", nullable = false)
    private EventType eventType;

    @Column(name = "published", nullable = false)
    private Boolean published;

    public void done(){
        this.published = true;
    }

}
