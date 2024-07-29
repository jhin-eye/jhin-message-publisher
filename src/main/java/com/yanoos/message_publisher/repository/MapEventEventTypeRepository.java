package com.yanoos.message_publisher.repository;

import com.yanoos.message_publisher.entity.event.Event;
import com.yanoos.message_publisher.entity.event.MapEventEventType;
import com.yanoos.message_publisher.entity.event.MapEventEventTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MapEventEventTypeRepository extends JpaRepository<MapEventEventType, MapEventEventTypeId> {
    @Modifying
    @Transactional
    @Query("UPDATE MapEventEventType m SET m.published = :published WHERE m.id = :id")
    void updatePublishedById(MapEventEventTypeId id, Boolean published);
}
