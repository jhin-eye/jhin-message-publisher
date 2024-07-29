package com.yanoos.message_publisher.repository;

import com.yanoos.message_publisher.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
