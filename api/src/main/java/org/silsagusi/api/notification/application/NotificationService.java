package org.silsagusi.api.notification.application;

import java.util.List;

import org.silsagusi.api.notification.application.dto.NotificationDto;
import org.silsagusi.api.notification.infrastructure.NotificationDataProvider;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationService {

	private final NotificationDataProvider notificationDataProvider;
	private final NotificationMapper notificationMapper;

	public SseEmitter subscribe(Long agentId) {
		SseEmitter emitter = notificationDataProvider.subscribe(agentId);
		return emitter;
	}

	public void notify(Long agentId, NotificationType type, String content) {
		notificationDataProvider.notify(agentId, type, content);
	}

	public List<NotificationDto.Response> getNotification(Long agentId) {
		List<Notification> notificationList = notificationDataProvider.getAllByAgent(agentId);
		return notificationList.stream().map(notificationMapper::toNotificationResponse).toList();
	}

	public void markRead(Long notificationId) {
		Notification notification = notificationDataProvider.getNotification(notificationId);
		notificationDataProvider.markNotificationRead(notification);
	}
}
