package org.silsagusi.joonggaemoa.domain.notify.service.dto;


import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.domain.notify.entity.Notification;
import org.silsagusi.joonggaemoa.domain.notify.entity.NotificationType;

public class NotificationDto {

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private NotificationType type;
        private String content;
        private boolean isRead;


        public static Response of(Notification notification) {
            return Response.builder()
                .id(notification.getId())
                .type(notification.getType())
                .content(notification.getContent())
                .isRead(notification.getIsRead())
                .build();
        }
    }

}
