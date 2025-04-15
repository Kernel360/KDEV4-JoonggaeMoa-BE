package org.silsagusi.joonggaemoa.api.message.domain;

import java.util.List;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;

public interface MessageTemplateDataProvider {

	void createDefaultMessageTemplate(Agent agent);

	void createMessageTemplate(MessageTemplate messageTemplate);

	List<MessageTemplate> getMessageTemplateList(Agent agent);

	MessageTemplate getMessageTemplateById(Long templateId);

	void updateMessageTemplate(MessageTemplate messageTemplate);

	void deleteMessageTemplate(MessageTemplate messageTemplate);

	void validateMessageTemplateWithAgent(MessageTemplate messageTemplate, Agent agent);
}
