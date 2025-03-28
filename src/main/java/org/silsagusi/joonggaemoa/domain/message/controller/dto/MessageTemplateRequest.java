package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTemplateRequest {

	private String category;
	private String content;
}
