package org.silsagusi.api.notification.application.service;

import java.util.List;

import org.silsagusi.api.notification.application.dto.NotificationDto;
import org.silsagusi.api.notification.infrastructure.dataprovider.NotificationDataProvider;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationService {

	private final NotificationDataProvider notificationDataProvider;

	@Transactional
	public SseEmitter subscribe(Long agentId, String clientId) {
		SseEmitter emitter = notificationDataProvider.subscribe(agentId, clientId);
		return emitter;
	}

	@Transactional
	public void notify(Long agentId, NotificationType type, String content) {
		notificationDataProvider.notify(agentId, type, content);
	}

	@Transactional(readOnly = true)
	public List<NotificationDto.Response> getNotification(Long agentId) {
		List<Notification> notificationList = notificationDataProvider.getAllByAgent(agentId);
		return notificationList.stream().map(NotificationDto::toResponse).toList();
	}

	@Transactional
	public void markRead(Long notificationId) {
		Notification notification = notificationDataProvider.getNotification(notificationId);
		notificationDataProvider.markNotificationRead(notification);
	}
}
