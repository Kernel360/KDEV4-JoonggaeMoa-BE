package org.silsagusi.api.message.application.service;

import java.util.List;

import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.message.application.dto.CreateMessageTemplateRequest;
import org.silsagusi.api.message.application.dto.MessageTemplateResponse;
import org.silsagusi.api.message.application.dto.UpdateMessageTemplateRequest;
import org.silsagusi.api.message.application.mapper.MessageTemplateMapper;
import org.silsagusi.api.message.application.validator.MessageTemplateValidator;
import org.silsagusi.api.message.infrastructure.dataProvider.MessageTemplateDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

	private final AgentDataProvider agentDataProvider;
	private final MessageTemplateDataProvider messageTemplateDataProvider;
	private final MessageTemplateMapper messageTemplateMapper;
	private final MessageTemplateValidator messageTemplateValidator;

	@Transactional
	public void createMessageTemplate(Long agentId, CreateMessageTemplateRequest messageTemplateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		MessageTemplate messageTemplate = messageTemplateMapper.toEntity(agent, messageTemplateRequest);

		messageTemplateDataProvider.createMessageTemplate(messageTemplate);
	}

	@Transactional(readOnly = true)
	public List<MessageTemplateResponse> getMessageTemplates(Long agentId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		List<MessageTemplate> messageTemplates = messageTemplateDataProvider.getMessageTemplateList(agent);

		return MessageTemplateResponse.toResponseList(messageTemplates);
	}

	@Transactional
	public void updateMessageTemplate(Long agentId, Long templateId, UpdateMessageTemplateRequest requestDto) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		MessageTemplate messageTemplate = messageTemplateDataProvider.getMessageTemplateById(templateId);

		messageTemplateValidator.validateAgentAccess(messageTemplate, agent);

		messageTemplateDataProvider.updateMessageTemplate(requestDto.getTitle(), requestDto.getContent(),
			messageTemplate);
	}

	@Transactional
	public void deleteMessageTemplate(Long agentId, Long templateId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		MessageTemplate messageTemplate = messageTemplateDataProvider.getMessageTemplateById(templateId);

		messageTemplateValidator.validateAgentAccess(messageTemplate, agent);

		messageTemplateDataProvider.deleteMessageTemplate(messageTemplate);
	}
}
