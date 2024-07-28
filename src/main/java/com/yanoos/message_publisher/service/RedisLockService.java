package com.yanoos.message_publisher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final RedisTemplate<String, String> redisTemplate;


    public boolean lock(String key, long timeout) {
        log.info("lock called");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean success = ops.setIfAbsent(key, "locked", timeout, TimeUnit.SECONDS);
        return success != null && success;
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}