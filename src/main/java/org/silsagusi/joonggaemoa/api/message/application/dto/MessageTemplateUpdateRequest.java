package org.silsagusi.joonggaemoa.api.message.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplateUpdateRequest {

    private String title;
    private String content;
}
