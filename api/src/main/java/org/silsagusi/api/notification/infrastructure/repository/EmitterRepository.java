package org.silsagusi.api.notification.infrastructure.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter save(String id, SseEmitter sseEmitter) {
		emitters.put(id, sseEmitter);
		return emitters.get(id);
	}

	public Optional<SseEmitter> get(String id) {
		return Optional.ofNullable(emitters.get(id));
	}

	public void remove(String id) {
		emitters.remove(id);
	}

	public List<Map.Entry<String, SseEmitter>> getAllEmittersByAgentId(Long agentId) {
		String prefix = agentId + ":";
		return emitters.entrySet()
			.stream()
			.filter(entry -> entry.getKey().startsWith(prefix))
			.collect(Collectors.toList());
	}
}