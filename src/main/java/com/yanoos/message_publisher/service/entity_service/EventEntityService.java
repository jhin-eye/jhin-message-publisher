package com.yanoos.message_publisher.service.entity_service;

import com.yanoos.message_publisher.entity.event.Event;
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

    public List<Event> getEventsByPublished(Boolean published) {
        return eventRepository.findByPublishedOrderByEventIdAsc(published);
    }
}
