package com.yanoos.message_publisher.repository;


import com.yanoos.message_publisher.entity.event.EventType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventTypeRepository extends JpaRepository<EventType,Long> {
}
