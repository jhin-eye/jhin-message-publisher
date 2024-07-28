package com.yanoos.message_publisher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventPublishService {
    private final RedisLockService redisLockService;

    private static final String LOCK_KEY = "event_publisher_lock";
    @Value("${lock.timeout}")
    private long LOCK_TIME;

    @Transactional
    public void publishEvents() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        if(redisLockService.lock(LOCK_KEY, LOCK_TIME)){
            try{
                //테스트용 시간경과
                Thread.sleep(9 * 1000);

                //미처리 이벤트 가져옴
                //메시지브로커에게 퍼블리싱
                //이벤트 처리상태 업데이트

                //경과시간 조사
                long elapsedTime = System.currentTimeMillis() - startTime;
                if(elapsedTime> LOCK_TIME * 1000){
                    throw new RuntimeException("Event processing took too long and lock expired");
                }

            }
            finally {
                redisLockService.unlock(LOCK_KEY);
            }
        }else{
            throw new RuntimeException("Unable to acquire lock, event processing unlocked");
        }
    }
}
