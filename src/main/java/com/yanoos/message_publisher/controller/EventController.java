package com.yanoos.message_publisher.controller;

import com.yanoos.message_publisher.service.EventPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Slf4j
@RequiredArgsConstructor
@Controller
public class EventController {
    private final EventPublishService eventPublishService;

    @ResponseBody
    @GetMapping("/publish")
    public String publishEvents() throws InterruptedException {
        eventPublishService.publishEvents();
        log.info("controller success {}",Thread.currentThread().getId());
        return "success";
    }
}
