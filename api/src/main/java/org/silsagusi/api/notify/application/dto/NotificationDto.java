package org.silsagusi.api.notify.application.dto;

import org.silsagusi.core.domain.notification.entity.NotificationType;

import lombok.Builder;
import lombok.Getter;

public class NotificationDto {

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private NotificationType type;
		private String content;
		private boolean isRead;
	}
}