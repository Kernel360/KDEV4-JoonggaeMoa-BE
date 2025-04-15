package org.silsagusi.joonggaemoa.api.notify.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.silsagusi.joonggaemoa.api.notify.domain.Notification;
import org.silsagusi.joonggaemoa.api.notify.domain.NotificationType;
import org.silsagusi.joonggaemoa.api.notify.infrastructure.EmitterRepository;
import org.silsagusi.joonggaemoa.api.notify.infrastructure.NotificationRepository;
import org.silsagusi.joonggaemoa.api.notify.application.dto.NotificationDto;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; //
	private final EmitterRepository emitterRepository;
	private final NotificationRepository notificationRepository;

	public SseEmitter subscribe(Long agentId) {
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitterRepository.save(agentId, emitter);

		emitter.onCompletion(() -> emitterRepository.remove(agentId));
		emitter.onTimeout(() -> emitterRepository.remove(agentId));

		notify(agentId, NotificationType.CONNECTION, "Connection Success! agentId: " + agentId);

		return emitter;
	}

	public void notify(Long agentId, NotificationType type, String content) {

		boolean alreadyNotified = notificationRepository.existsByAgentIdAndTypeAndContent(agentId, type, content);

		if (alreadyNotified) {
			log.info("이미 같은 알림이 전송됨: agentId={}, content={}", agentId, content);
			return;
		}

		Notification notification = new Notification(
			agentId, type, content
		);

		if (type != NotificationType.CONNECTION) {
			notificationRepository.save(notification);
		}

		SseEmitter emitter = emitterRepository.get(agentId);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
					.name("notification")
					.data(notification));
			} catch (IOException e) {
				emitterRepository.remove(agentId);
			}
		}

	}

	public List<NotificationDto.Response> getNotification(Long agentId) {
		List<Notification> notificationList = notificationRepository.findAll();
		//List<Notification> notificationList = notificationRepository.findByAgentIdAndIsReadFalse(agentId);
		return notificationList.stream().map(NotificationDto.Response::of).toList();
	}

	public void markRead(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
		notification.markRead();
		notificationRepository.save(notification);
	}
}
