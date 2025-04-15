package org.silsagusi.joonggaemoa.api.message.domain.dataProvider;

import java.util.List;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.message.domain.entity.MessageTemplate;

public interface MessageTemplateDataProvider {

	void createDefaultMessageTemplate(Agent agent);

	void createMessageTemplate(MessageTemplate messageTemplate);

	List<MessageTemplate> getMessageTemplateList(Agent agent);

	MessageTemplate getMessageTemplateById(Long templateId);

	void updateMessageTemplate(MessageTemplate messageTemplate);

	void deleteMessageTemplate(MessageTemplate messageTemplate);

	void validateMessageTemplateWithAgent(MessageTemplate messageTemplate, Agent agent);
}
