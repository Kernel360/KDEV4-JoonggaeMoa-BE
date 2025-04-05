package org.silsagusi.joonggaemoa.domain.notify.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.notify.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/api/notification/subscribe")
    public SseEmitter subscribe(
        HttpServletRequest request
    ) {
        SseEmitter sseEmitter = notificationService.subscribe((Long) request.getAttribute("agentId"));
        return sseEmitter;
    }

}
