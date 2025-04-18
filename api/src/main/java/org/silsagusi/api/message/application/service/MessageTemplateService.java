package org.silsagusi.api.message.application.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.agent.infrastructure.AgentDataProvider;
import org.silsagusi.api.message.application.dto.MessageTemplateDto;
import org.silsagusi.api.message.application.dto.UpdateMessageTemplateRequest;
import org.silsagusi.api.message.application.mapper.MessageTemplateMapper;
import org.silsagusi.api.message.infrastructure.dataProvider.MessageTemplateDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

    private final AgentDataProvider agentDataProvider;
    private final MessageTemplateDataProvider messageTemplateDataProvider;
    private final MessageTemplateMapper messageTemplateMapper;

    public void createMessageTemplate(Long agentId, MessageTemplateDto.Request messageTemplateRequest) {
        Agent agent = agentDataProvider.getAgentById(agentId);
        MessageTemplate messageTemplate = messageTemplateMapper.toEntity(agent, messageTemplateRequest);

        messageTemplateDataProvider.createMessageTemplate(messageTemplate);
    }

    public List<MessageTemplateDto.Response> getMessageTemplates(Long agentId) {
        Agent agent = agentDataProvider.getAgentById(agentId);
        List<MessageTemplate> messageTemplates = messageTemplateDataProvider.getMessageTemplateList(agent);

        return messageTemplateMapper.toResponseDtos(messageTemplates);
    }

    public void updateMessageTemplate(Long agentId, Long templateId, UpdateMessageTemplateRequest requestDto) {
        Agent agent = agentDataProvider.getAgentById(agentId);
        MessageTemplate messageTemplate = messageTemplateDataProvider.getMessageTemplateById(templateId);

        messageTemplateDataProvider.validateMessageTemplateWithAgent(messageTemplate, agent);

        messageTemplateDataProvider.updateMessageTemplate(requestDto.getTitle(), requestDto.getContent(), messageTemplate);
    }

    public void deleteMessageTemplate(Long agentId, Long templateId) {
        Agent agent = agentDataProvider.getAgentById(agentId);
        MessageTemplate messageTemplate = messageTemplateDataProvider.getMessageTemplateById(templateId);

        messageTemplateDataProvider.validateMessageTemplateWithAgent(messageTemplate, agent);

        messageTemplateDataProvider.deleteMessageTemplate(messageTemplate);
    }
}
