package org.silsagusi.api.notification.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.notification.entity.Notification;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {
	private Long id;
	private String type;
	private String content;
	private Boolean isRead;
	private LocalDateTime createdAt;

	public static NotificationResponse toResponse(Notification notification) {
		return NotificationResponse.builder()
			.id(notification.getId())
			.type(notification.getType() + "")
			.content(notification.getContent())
			.isRead(notification.getIsRead())
			.createdAt(notification.getCreatedAt())
			.build();
	}
}
