package org.silsagusi.api.notification.infrastructure;

import java.io.IOException;
import java.util.List;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationDataProvider {

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
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

	public List<Notification> getAllByAgent(Long agentId) {
		return notificationRepository.findAllByAgentId(agentId);
	}

	public Notification getNotification(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
		return notification;
	}

	public void markNotificationRead(Notification notification) {
		notification.markRead();
		notificationRepository.save(notification);
	}

}
