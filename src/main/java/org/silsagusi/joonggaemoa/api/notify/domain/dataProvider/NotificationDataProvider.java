package org.silsagusi.joonggaemoa.api.notify.domain.dataProvider;

import org.silsagusi.joonggaemoa.api.notify.domain.entity.Notification;
import org.silsagusi.joonggaemoa.api.notify.domain.entity.NotificationType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationDataProvider {
    SseEmitter subscribe(Long agentId);

    void notify(Long agentId, NotificationType type, String content);

    List<Notification> getAllByAgent(Long agentId);

    Notification getNotification(Long notificationId);

    void markNotificationRead(Notification notification);
}
