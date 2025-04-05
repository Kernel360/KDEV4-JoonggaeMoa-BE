package org.silsagusi.joonggaemoa.domain.notify.service;


import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.notify.entity.Notification;
import org.silsagusi.joonggaemoa.domain.notify.repository.EmitterRepository;
import org.silsagusi.joonggaemoa.domain.notify.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

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

        notify(agentId, "Connection", "Connection Success! agentId: " + agentId);

        return emitter;
    }

    public void notify(Long agentId, String type, String content) {
        Notification notification = new Notification(
            agentId, type, content
        );

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

}
