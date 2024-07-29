package com.yanoos.message_publisher.repository;

import com.yanoos.message_publisher.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByFinished(boolean b);
}
