package org.silsagusi.joonggaemoa.api.message.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpdateRequest {

    private String content;
    private LocalDateTime sendAt;
}
