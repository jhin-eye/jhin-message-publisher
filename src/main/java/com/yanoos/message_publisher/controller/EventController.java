package com.yanoos.message_publisher.controller;

import com.yanoos.message_publisher.service.EventPublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class EventController {
    private final EventPublishService eventPublishService;

    @ResponseBody
    @GetMapping("/publish")
    public String publishEvents() throws InterruptedException {
        eventPublishService.publishEvents();
        return "success";
    }
}
