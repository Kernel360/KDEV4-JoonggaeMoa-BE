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

	private Long id;
	private String title;
	private String content;

	public static MessageTemplateCommand of(MessageTemplate messageTemplate) {
		return MessageTemplateCommand.builder()
			.id(messageTemplate.getId())
			.title(messageTemplate.getTitle())
			.content(messageTemplate.getContent())
			.build();
	}
}
