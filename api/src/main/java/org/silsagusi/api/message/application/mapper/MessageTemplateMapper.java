package org.silsagusi.api.message.application.mapper;

import java.util.List;

import org.silsagusi.api.message.application.dto.MessageTemplateDto;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageTemplateMapper {

	public MessageTemplate toEntity(Agent agent, MessageTemplateDto.Request messageTemplateRequest) {
		return MessageTemplate.create(agent, messageTemplateRequest.getTitle(), messageTemplateRequest.getContent());
	}

	public List<MessageTemplateDto.Response> toResponseDtos(List<MessageTemplate> messageTemplates) {
		return messageTemplates.stream()
			.map(messageTemplate ->
				MessageTemplateDto.Response.builder()
					.id(messageTemplate.getId())
					.title(messageTemplate.getTitle())
					.content(messageTemplate.getContent())
					.build()
			).toList();
	}
}
