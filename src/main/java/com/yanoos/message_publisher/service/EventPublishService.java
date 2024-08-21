package com.yanoos.message_publisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanoos.message_publisher.entity.event.Event;
import com.yanoos.message_publisher.service.entity_service.EventEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    @Value("${lock.timeout}")
    private long LOCK_TIME;

    @Scheduled(cron = "${scheduled.cron}")
    @Transactional
    public void publishUnFinishedEvents() throws InterruptedException, JsonProcessingException, ExecutionException {
        log.info("publishUnFinishedEvents");
        long startTime = System.currentTimeMillis();
        if(redisLockService.lock(LOCK_KEY, LOCK_TIME)){
            log.info("{} get lock!", Thread.currentThread().getId());
            try{
                //미처리 이벤트 가져옴
                List<Event> unFinishedEvents = eventEntityService.getEventsByPublished(false);
                // unFinishedEvents = unFinishedEvents.subList(0, Math.min(unFinishedEvents.size(), 10));//TODO 테스트 종료 후 제거
                //메시지브로커에게 퍼블리싱
                for(Event event : unFinishedEvents){

                    JsonNode eventDataNode = objectMapper.readTree(event.getEventData());
                    // JSON 변환
                    Map<String, Object> jsonMap = new HashMap<>();
                    jsonMap.put("eventId", event.getEventId());
                    jsonMap.put("eventType", event.getEventType());
                    jsonMap.put("value", eventDataNode);
                    JsonNode jsonMessage = objectMapper.valueToTree(jsonMap);

                    kafkaProducer.sendMessage(event.getEventType(), jsonMessage.toString());
                    //이벤트 처리 완료
                    event.done();

                    //경과시간 조사하여 LOCK_TIME(10초) 이상 물고있었으면 멈춤
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if(elapsedTime> LOCK_TIME * 1000){
                        break;
                    }
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

}
