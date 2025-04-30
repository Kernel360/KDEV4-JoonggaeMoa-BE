package org.silsagusi.api.notification.infrastructure.dataprovider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.notification.infrastructure.repository.EmitterRepository;
import org.silsagusi.api.notification.infrastructure.repository.NotificationRepository;
import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.http.MediaType;
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
	private static final long RECONNECTION_TIMEOUT = 1000L;
	private final EmitterRepository emitterRepository;
	private final NotificationRepository notificationRepository;

	public SseEmitter subscribe(Long agentId, String clientId) {

		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		String emitterId = agentId + ":" + clientId;
		emitterRepository.save(emitterId, emitter);

		emitter.onCompletion(() -> {
			log.info("SSE completed: agentId={}", agentId);
			emitterRepository.remove(emitterId);
		});
		emitter.onTimeout(() -> {
			log.warn("SSE timeout: agentId={}", agentId);
			emitterRepository.remove(emitterId);
		});
		emitter.onError((e) -> {
			log.error("SSE error: agentId={}, error={}", agentId, e.getMessage());
			emitterRepository.remove(emitterId);
		});

		try {
			emitter.send(
				SseEmitter.event()
					.name("notification")
					.reconnectTime(RECONNECTION_TIMEOUT)
					.data("connection", MediaType.APPLICATION_JSON)
			);
		} catch (IOException e) {
			log.error("Failed to send notification to agentId {}: {}", emitterId, e.getMessage());
			emitter.completeWithError(e);
			emitterRepository.remove(emitterId);
		}

		return emitter;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void notify(Long agentId, NotificationType type, String content) {

		if (type != NotificationType.SURVEY) {
			boolean alreadyNotified = notificationRepository.existsByAgentIdAndTypeAndContent(agentId, type, content);
			if (alreadyNotified) {
				log.info("이미 같은 알림이 전송됨: agentId={}, content={}", agentId, content);
				return;
			}
		}

		if (type == NotificationType.CONNECTION) {
			return;
		}

		List<Map.Entry<String, SseEmitter>> emitters = emitterRepository.getAllEmittersByAgentId(agentId);
		
		for (Map.Entry<String, SseEmitter> entry : emitters) {
			String emitterId = entry.getKey();
			SseEmitter emitter = entry.getValue();

			Notification notification = Notification.create(agentId, type, content);

			if (emitter != null) {
				try {
					emitter.send(
						SseEmitter.event()
							.name("notification")
							.reconnectTime(RECONNECTION_TIMEOUT)
							.data(notification, MediaType.APPLICATION_JSON)
					);
				} catch (Exception e) {
					log.error("Failed to send notification to agentId {}: {}", emitterId, e.getMessage());
					emitter.completeWithError(e);
					emitterRepository.remove(emitterId);
				}
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
