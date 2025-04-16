package org.silsagusi.api.notify.application;

import java.util.List;

import org.silsagusi.api.notify.application.dto.NotificationDto;
import org.silsagusi.api.notify.infrastructure.NotificationDataProvider;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationService {

	private NotificationDataProvider notificationDataProvider;

	public SseEmitter subscribe(Long agentId) {
		SseEmitter emitter = notificationDataProvider.subscribe(agentId);
		return emitter;
	}

	public void notify(Long agentId, NotificationType type, String content) {
		notificationDataProvider.notify(agentId, type, content);
	}

	public List<NotificationDto.Response> getNotification(Long agentId) {
		List<Notification> notificationList = notificationDataProvider.getAllByAgent(agentId);
		return notificationList.stream().map(NotificationDto.Response::of).toList();
	}

	public void markRead(Long notificationId) {
		Notification notification = notificationDataProvider.getNotification(notificationId);
		notificationDataProvider.markNotificationRead(notification);
	}
}
