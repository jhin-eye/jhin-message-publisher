package com.yanoos.message_publisher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanoos.message_publisher.service.EventPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class EventController {
    private final EventPublishService eventPublishService;


    @ResponseBody
    @GetMapping("/publish")
    public String publishEvents() throws InterruptedException, ExecutionException, JsonProcessingException {
        eventPublishService.publishUnFinishedEvents();
        return "success";
    }


    @GetMapping("/test")
    @ResponseBody
    public String test() {
        log.info("test");
        return "test";
    }
}
