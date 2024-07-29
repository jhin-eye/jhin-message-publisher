package com.yanoos.message_publisher.service.entity_service;

import com.yanoos.message_publisher.entity.event.Event;
import com.yanoos.message_publisher.entity.event.EventType;
import com.yanoos.message_publisher.entity.event.MapEventEventType;
import com.yanoos.message_publisher.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventEntityService {
    private final EventRepository eventRepository;

    public Event getEventByEventId(long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow();
        log.info("event {}", event.getEventData());
        List<MapEventEventType> mapEventEventTypes = event.getMapEventEventTypes();
        for(MapEventEventType mapEventEventType: mapEventEventTypes){
            EventType eventType = mapEventEventType.getEventType();
            log.info("ss: {}",mapEventEventType.getEventType().getEventType());
        }
        return event;
    }

    public List<Event> getEventsByFinished(Boolean finished) {
        return eventRepository.findByFinishedOrderByEventIdAsc(finished);
    }
}
