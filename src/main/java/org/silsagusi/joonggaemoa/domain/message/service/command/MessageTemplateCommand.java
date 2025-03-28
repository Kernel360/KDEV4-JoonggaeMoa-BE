package org.silsagusi.joonggaemoa.domain.message.service.command;

import org.silsagusi.joonggaemoa.domain.message.entity.MessageTemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTemplateCommand {

	private String category;
	private String content;

	public static MessageTemplateCommand of(MessageTemplate messageTemplate) {
		return MessageTemplateCommand.builder()
			.category(String.valueOf(messageTemplate.getCategory()))
			.content(messageTemplate.getContent())
			.build();
	}
}
