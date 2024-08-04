package com.yanoos.message_publisher.repository;

import com.yanoos.message_publisher.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByPublishedOrderByEventIdAsc(boolean b);
}
