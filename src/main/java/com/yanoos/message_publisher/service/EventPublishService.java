package com.yanoos.message_publisher.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanoos.message_publisher.entity.event.Event;
import com.yanoos.message_publisher.entity.event.MapEventEventType;
import com.yanoos.message_publisher.repository.EventRepository;
import com.yanoos.message_publisher.repository.MapEventEventTypeRepository;
import com.yanoos.message_publisher.service.entity_service.EventEntityService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventPublishService {
    private final RedisLockService redisLockService;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String LOCK_KEY = "event_publisher_lock";
    private final EventEntityService eventEntityService;
    private final MapEventEventTypeRepository mapEventEventTypeRepository;
    private final EventRepository eventRepository;
    @Value("${lock.timeout}")
    private long LOCK_TIME;

    @Transactional
    public void publishUnFinishedEvents() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        if(redisLockService.lock(LOCK_KEY, LOCK_TIME)){
            log.info("{} get lock!", Thread.currentThread().getId());
            try{
                //테스트용 시간경과
//                Thread.sleep(9 * 1000);

                //미처리 이벤트 가져옴
                List<Event> unFinishedEvents = eventEntityService.getEventsByFinished(false);
                unFinishedEvents = unFinishedEvents.subList(0,10);
                //메시지브로커에게 퍼블리싱
                for(Event event : unFinishedEvents){
                    messagePublish(event);
                }
                //경과시간 조사
                long elapsedTime = System.currentTimeMillis() - startTime;
                if(elapsedTime> LOCK_TIME * 1000){
                    throw new RuntimeException("Event processing took too long and lock expired");
                }

            }
            finally {
                log.info("{} unlock!", Thread.currentThread().getId());
                redisLockService.unlock(LOCK_KEY);
            }
        }else{
            log.info("{} couldn't get lock!", Thread.currentThread().getId());
            throw new RuntimeException("Unable to acquire lock, event processing unlocked");
        }
    }

    @Transactional
    protected void messagePublish(Event event) {
        List<MapEventEventType> mapEventEventTypes = event.getMapEventEventTypes();
        int mapCount = mapEventEventTypes.size();//반환시 0이면 event에 해당하는 모든 메세지 퍼블된것 == 다시 볼 필요 없는 이벤트가 된다

        for(MapEventEventType mapEventEventType: mapEventEventTypes){
            if(publishOneMapEventEventType(event, mapEventEventType)){
                mapCount --;
            }
        }

        if(mapCount==0){
            eventRepository.updateFinishedById(event.getEventId(),true);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected boolean publishOneMapEventEventType(Event event, MapEventEventType mapEventEventType) {

        // JSON 변환
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("eventId", event.getEventId());  // '내가정한id'를 실제 ID로 변경 필요
        jsonMap.put("eventTypeId", mapEventEventType.getEventType().getEventTypeId());  // '내가정한id'를 실제 ID로 변경 필요
        jsonMap.put("val", event.getEventData());
        JsonNode jsonMessage = objectMapper.valueToTree(jsonMap);

        boolean successSendMessage = kafkaProducer.sendMessage(mapEventEventType.getEventType().getEventType(), jsonMessage.toString());
        if(successSendMessage){
            mapEventEventTypeRepository.updatePublishedById(mapEventEventType.getId(),true);
            return true;
        }

        return false;

    }
}
