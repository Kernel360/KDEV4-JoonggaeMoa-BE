package org.silsagusi.api.message.application.dto;

import java.util.List;

import org.silsagusi.core.domain.message.entity.MessageTemplate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageTemplateResponse {
	private Long id;
	private String title;
	private String content;

	public static List<MessageTemplateResponse> toResponses(List<MessageTemplate> messageTemplates) {
		return messageTemplates.stream()
			.map(messageTemplate ->
				MessageTemplateResponse.builder()
					.id(messageTemplate.getId())
					.title(messageTemplate.getTitle())
					.content(messageTemplate.getContent())
					.build()
			).toList();
	}
}
