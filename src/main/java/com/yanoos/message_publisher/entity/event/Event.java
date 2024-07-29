package com.yanoos.message_publisher.entity.event;


import com.fasterxml.jackson.databind.JsonNode;
import com.yanoos.message_publisher.entity.converter.JsonNodeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "event_data", columnDefinition = "json", nullable = false)
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode eventData;

    @Column(name = "finished", nullable = false)
    private Boolean finished;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    List<MapEventEventType> mapEventEventTypes = new ArrayList<>();

}
