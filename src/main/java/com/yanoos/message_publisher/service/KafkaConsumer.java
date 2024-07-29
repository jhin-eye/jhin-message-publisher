package com.yanoos.message_publisher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "NEW_POST", groupId = "my-group")
    public void consume(String message){
        log.info("Consumed message: {}",message);
    }
}
