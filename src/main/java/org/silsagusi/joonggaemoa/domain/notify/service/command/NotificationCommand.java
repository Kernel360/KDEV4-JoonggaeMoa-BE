package org.silsagusi.joonggaemoa.domain.notify.service.command;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.domain.notify.entity.Notification;
import org.silsagusi.joonggaemoa.domain.notify.entity.NotificationType;

@Getter
@Builder
public class NotificationCommand {
    private Long id;
    private NotificationType type;
    private String content;
    private Boolean isRead;

    public static NotificationCommand of(Notification notification) {
        return NotificationCommand.builder()
            .id(notification.getId())
            .type(notification.getType())
            .content(notification.getContent())
            .isRead(notification.getIsRead())
            .build();
    }
}
