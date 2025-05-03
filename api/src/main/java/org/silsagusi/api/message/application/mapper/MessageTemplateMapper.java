package org.silsagusi.api.message.application.mapper;

import org.silsagusi.api.message.application.dto.CreateMessageTemplateRequest;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageTemplateMapper {

	public MessageTemplate toEntity(Agent agent, CreateMessageTemplateRequest messageTemplateRequest) {
		return MessageTemplate.create(agent, messageTemplateRequest.getTitle(), messageTemplateRequest.getContent());
	}
}
