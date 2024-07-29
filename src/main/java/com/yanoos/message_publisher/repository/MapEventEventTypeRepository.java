package com.yanoos.message_publisher.repository;

import com.yanoos.message_publisher.entity.event.Event;
import com.yanoos.message_publisher.entity.event.MapEventEventType;
import com.yanoos.message_publisher.entity.event.MapEventEventTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapEventEventTypeRepository extends JpaRepository<MapEventEventType, MapEventEventTypeId> {
}
