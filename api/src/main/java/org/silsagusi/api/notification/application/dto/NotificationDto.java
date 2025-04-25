package org.silsagusi.api.notification.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDto {

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private NotificationType type;
		private String content;
		private boolean isRead;
		private LocalDateTime createdAt;
	}

	public static Response toResponse(Notification notification) {
		return Response.builder()
			.id(notification.getId())
			.type(notification.getType())
			.content(notification.getContent())
			.isRead(notification.getIsRead())
			.createdAt(notification.getCreatedAt())
			.build();
	}
}