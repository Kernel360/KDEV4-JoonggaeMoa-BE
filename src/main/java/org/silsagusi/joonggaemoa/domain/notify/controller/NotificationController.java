package org.silsagusi.joonggaemoa.domain.notify.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.notify.controller.dto.NotificationDto;
import org.silsagusi.joonggaemoa.domain.notify.service.NotificationService;
import org.silsagusi.joonggaemoa.domain.notify.service.command.NotificationCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/api/notification/subscribe")
    public SseEmitter subscribe(
        HttpServletRequest request
    ) {
        SseEmitter sseEmitter = notificationService.subscribe((Long) request.getAttribute("agentId"));
        return sseEmitter;
    }

    @GetMapping("/api/notification")
    public ResponseEntity<ApiResponse<List<NotificationDto.Response>>> getNotification(
        HttpServletRequest request
    ) {
        List<NotificationCommand> notificationCommand = notificationService.getNotification((Long) request.getAttribute("agentId"));
        List<NotificationDto.Response> notification = notificationCommand.stream().map(NotificationDto.Response::of).toList();

        return ResponseEntity.ok(ApiResponse.ok(notification));
    }

    @PatchMapping("/api/notification/read")
    public ResponseEntity<ApiResponse<List<Void>>> readNotification(
        @PathParam("notificationId") Long notificationId
    ) {
        notificationService.markRead(notificationId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
