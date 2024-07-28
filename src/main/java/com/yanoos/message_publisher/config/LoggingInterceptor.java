package com.yanoos.message_publisher.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH시:mm분:ss.SS초");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        log.info("Request URL: {} :: Start Time = {} :: Thread ID = {}", request.getRequestURL(), dateFormat.format(new Date(startTime)), Thread.currentThread().getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        log.info("Request URL: {} :: End Time = {} :: Thread ID = {}", request.getRequestURL(), dateFormat.format(new Date(endTime)), Thread.currentThread().getId());
        log.info("Request URL: {} :: Time Taken = {} ms", request.getRequestURL(), (endTime - startTime));
    }
}
