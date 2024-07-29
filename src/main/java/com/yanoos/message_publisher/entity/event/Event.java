package com.yanoos.message_publisher.entity.event;


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

    @Column(name = "event_data", nullable = false)
    private String eventData;

    @Column(name = "finished", nullable = false)
    private Boolean finished;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    List<MapEventEventType> mapEventEventTypes = new ArrayList<>();

    public void done(){
        this.finished = true;
    }
}
