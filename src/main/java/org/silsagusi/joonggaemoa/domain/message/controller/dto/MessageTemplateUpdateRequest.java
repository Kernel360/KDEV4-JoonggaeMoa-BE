package org.silsagusi.joonggaemoa.domain.message.controller.dto;

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
