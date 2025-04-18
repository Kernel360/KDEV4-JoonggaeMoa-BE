package org.silsagusi.api.notify.application;

import org.silsagusi.api.notify.application.dto.NotificationDto;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

	public NotificationDto.Response toNotificationResponse(Notification notification) {
		return NotificationDto.Response.builder()
			.id(notification.getId())
			.type(notification.getType())
			.content(notification.getContent())
			.isRead(notification.getIsRead())
			.build();
	}
}
