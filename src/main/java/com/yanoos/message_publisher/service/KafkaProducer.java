package com.yanoos.message_publisher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public boolean sendMessage(String topic, String message) throws ExecutionException, InterruptedException {
        //메시지 전송
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);


        SendResult<String, String> result = future.get();
        RecordMetadata recordMetadata = result.getRecordMetadata();
        log.info("Sent message=[{}] with offset=[{}], partition=[{}], topic=[{}]",
                message, recordMetadata.offset(), recordMetadata.partition(), recordMetadata.topic());
        return true;

    }
}
