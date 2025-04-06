package org.silsagusi.joonggaemoa.domain.notify.controller.dto;


import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.domain.notify.entity.NotificationType;
import org.silsagusi.joonggaemoa.domain.notify.service.command.NotificationCommand;

public class NotificationDto {

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private NotificationType type;
        private String content;


        public static Response of(NotificationCommand command) {
            return Response.builder()
                .id(command.getId())
                .type(command.getType())
                .content(command.getContent())
                .build();
        }
    }

}
