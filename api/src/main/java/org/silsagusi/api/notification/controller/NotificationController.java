package org.silsagusi.api.notification.controller;

import java.util.List;

import org.silsagusi.api.common.auth.jwt.JwtProvider;
import org.silsagusi.api.notification.application.dto.NotificationDto;
import org.silsagusi.api.notification.application.service.NotificationService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NotificationController {

	private final NotificationService notificationService;
	private final JwtProvider jwtProvider;

	@GetMapping("/api/notification/subscribe")
	public SseEmitter subscribe(
		@RequestParam("agentId") Long agentId
	) {
		SseEmitter sseEmitter = notificationService.subscribe(agentId);
		return sseEmitter;
	}

	@GetMapping("/api/notification")
	public ResponseEntity<ApiResponse<List<NotificationDto.Response>>> getNotification(
		HttpServletRequest request
	) {
		List<NotificationDto.Response> notification = notificationService.getNotification(
			(Long)request.getAttribute("agentId"));

		return ResponseEntity.ok(ApiResponse.ok(notification));
	}

	@PatchMapping("/api/notification/read")
	public ResponseEntity<ApiResponse<List<Void>>> readNotification(
		@RequestParam("notificationId") Long notificationId
	) {
		notificationService.markRead(notificationId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}
