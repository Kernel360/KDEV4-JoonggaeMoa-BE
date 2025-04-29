package org.silsagusi.api.notification.infrastructure.dataprovider;

import java.util.List;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.notification.infrastructure.repository.EmitterRepository;
import org.silsagusi.api.notification.infrastructure.repository.NotificationRepository;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

		emitter.onCompletion(() -> {
			log.info("SSE completed: agentId={}", agentId);
			emitterRepository.remove(agentId);
		});
		emitter.onTimeout(() -> {
			log.warn("SSE timeout: agentId={}", agentId);
			emitterRepository.remove(agentId);
		});
		emitter.onError((e) -> {
			log.error("SSE error: agentId={}, error={}", agentId, e.getMessage());
			emitterRepository.remove(agentId);
		});

		notify(agentId, NotificationType.CONNECTION, "로그인에 성공했습니다!");

		return emitter;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void notify(Long agentId, NotificationType type, String content) {

		Notification notification = Notification.create(
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
			} catch (Exception e) {
				log.error("Failed to send notification to agentId {}: {}", agentId, e.getMessage());
				emitter.completeWithError(e);
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
	}

}
