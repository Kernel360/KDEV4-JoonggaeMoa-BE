package org.silsagusi.joonggaemoa.api.message.application;

import java.util.List;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.core.domain.agent.AgentDataProvider;
import org.silsagusi.joonggaemoa.api.message.application.dto.MessageTemplateDto;
import org.silsagusi.joonggaemoa.api.message.application.dto.MessageTemplateUpdateRequest;
import org.silsagusi.joonggaemoa.core.domain.message.dataProvider.MessageTemplateDataProvider;
import org.silsagusi.joonggaemoa.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

	private final AgentDataProvider agentDataProvider;
	private final MessageTemplateDataProvider messageTemplateDataProvider;

	public void createMessageTemplate(Long agentId, MessageTemplateDto.Request messageTemplateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		MessageTemplate messageTemplate = MessageTemplate.create(agent, messageTemplateRequest.getTitle(),
			messageTemplateRequest.getContent());
		messageTemplateDataProvider.createMessageTemplate(messageTemplate);
	}

	public List<MessageTemplateDto.Response> getMessageTemplateList(Long agentId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		List<MessageTemplate> messageTemplateList = messageTemplateDataProvider.getMessageTemplateList(agent);
		return messageTemplateList.stream()
			.map(MessageTemplateDto.Response::of)
			.toList();
	}

	public void updateMessageTemplate(Long agentId, Long templateId, MessageTemplateUpdateRequest requestDto) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		MessageTemplate messageTemplate = messageTemplateDataProvider.getMessageTemplateById(templateId);
		messageTemplateDataProvider.validateMessageTemplateWithAgent(messageTemplate, agent);
		messageTemplate.updateMessageTemplate(requestDto.getTitle(), requestDto.getContent());
		messageTemplateDataProvider.updateMessageTemplate(messageTemplate);
	}

	public void deleteMessageTemplate(Long agentId, Long templateId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		MessageTemplate messageTemplate = messageTemplateDataProvider.getMessageTemplateById(templateId);
		messageTemplateDataProvider.validateMessageTemplateWithAgent(messageTemplate, agent);
		messageTemplateDataProvider.deleteMessageTemplate(messageTemplate);
	}
}
