package org.silsagusi.joonggaemoa.api.notify.infrastructure;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long agentId, SseEmitter sseEmitter) {
        emitters.put(agentId, sseEmitter);
        return emitters.get(agentId);
    }

    public SseEmitter get(Long agentId) {
        return emitters.get(agentId);
    }

    public void remove(Long agentId) {
        emitters.remove(agentId);
    }

}