package org.silsagusi.api.notification.controller;

import java.util.List;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.notification.application.dto.NotificationDto;
import org.silsagusi.api.notification.application.service.NotificationService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping(value = "/api/notifications/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribe(
		@RequestParam("agentId") Long agentId,
		@RequestParam("clientId") String clientId
	) {
		SseEmitter sseEmitter = notificationService.subscribe(agentId, clientId);
		return ResponseEntity.ok(sseEmitter);
	}

	@GetMapping("/api/notifications")
	public ResponseEntity<ApiResponse<List<NotificationDto.Response>>> getNotifications(
		@CurrentAgentId Long agentId
	) {
		List<NotificationDto.Response> notification = notificationService.getNotifications(agentId);
		return ResponseEntity.ok(ApiResponse.ok(notification));
	}

	@GetMapping("/api/notifiactions/{notificationId}")
	public ResponseEntity<ApiResponse<NotificationDto.Response>> getNotification(
		@PathVariable Long notificationId
	) {
		NotificationDto.Response notification = notificationService.getNotification(notificationId);

		return ResponseEntity.ok(ApiResponse.ok(notification));
	}

	@PatchMapping("/api/notifications/{notificationId}")
	public ResponseEntity<ApiResponse<Void>> readNotification(
		@PathVariable Long notificationId
	) {
		notificationService.markRead(notificationId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}
