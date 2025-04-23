package org.silsagusi.api.message.infrastructure.validator;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageTemplateValidator {

	public void validateAgentAccess(MessageTemplate messageTemplate, Agent agent) {
		if (!messageTemplate.getAgent().equals(agent)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}
