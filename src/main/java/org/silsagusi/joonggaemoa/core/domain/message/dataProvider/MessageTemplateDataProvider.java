package org.silsagusi.joonggaemoa.core.domain.message.dataProvider;

import java.util.List;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.core.domain.message.entity.MessageTemplate;

public interface MessageTemplateDataProvider {

	void createDefaultMessageTemplate(Agent agent);

	void createMessageTemplate(MessageTemplate messageTemplate);

	List<MessageTemplate> getMessageTemplateList(Agent agent);

	MessageTemplate getMessageTemplateById(Long templateId);

	void updateMessageTemplate(MessageTemplate messageTemplate);

	void deleteMessageTemplate(MessageTemplate messageTemplate);

	void validateMessageTemplateWithAgent(MessageTemplate messageTemplate, Agent agent);
}
