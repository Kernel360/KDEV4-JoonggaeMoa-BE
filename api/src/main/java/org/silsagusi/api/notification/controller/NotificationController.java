package org.silsagusi.api.notification.controller;

import java.util.List;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.notification.application.dto.NotificationResponse;
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
	public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotifications(
		@CurrentAgentId Long agentId
	) {
		List<NotificationResponse> notificationResponses = notificationService.getNotifications(agentId);
		return ResponseEntity.ok(ApiResponse.ok(notificationResponses));
	}

	@GetMapping("/api/notifiactions/{notificationId}")
	public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
		@PathVariable Long notificationId
	) {
		NotificationResponse notificationResponse = notificationService.getNotification(notificationId);
		return ResponseEntity.ok(ApiResponse.ok(notificationResponse));
	}

	@PatchMapping("/api/notifications/{notificationId}")
	public ResponseEntity<ApiResponse<Void>> readNotification(
		@PathVariable Long notificationId
	) {
		notificationService.markRead(notificationId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}
