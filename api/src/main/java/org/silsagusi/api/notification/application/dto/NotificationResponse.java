package org.silsagusi.api.notification.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {
	private Long id;
	private NotificationType type;
	private String content;
	private boolean isRead;
	private LocalDateTime createdAt;

	public static NotificationResponse toResponse(Notification notification) {
		return NotificationResponse.builder()
			.id(notification.getId())
			.type(notification.getType())
			.content(notification.getContent())
			.isRead(notification.getIsRead())
			.createdAt(notification.getCreatedAt())
			.build();
	}
}
