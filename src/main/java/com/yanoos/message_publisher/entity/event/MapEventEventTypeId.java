package com.yanoos.message_publisher.entity.event;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MapEventEventTypeId implements Serializable {

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_type_id")
    private Long eventTypeId;
}
