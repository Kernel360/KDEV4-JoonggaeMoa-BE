package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import org.silsagusi.joonggaemoa.domain.message.service.command.MessageTemplateCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTemplateResponse {

	private String category;
	private String content;

	public static MessageTemplateResponse of(MessageTemplateCommand messageTemplateCommand) {
		return MessageTemplateResponse.builder()
			.category(messageTemplateCommand.getCategory())
			.content(messageTemplateCommand.getContent())
			.build();
	}
}
