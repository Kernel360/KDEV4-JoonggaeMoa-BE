package org.silsagusi.api.message.application.validator;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.Message;
import org.silsagusi.core.domain.message.entity.SendStatus;
import org.springframework.stereotype.Component;

@Component
public class MessageValidator {

	public void validateAgentAccess(Message message, Agent agent) {
		if (!agent.equals(message.getCustomer().getAgent())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
	}

	public void validateMessageStatusEqualsPending(Message message) {
		if (message.getSendStatus() != SendStatus.PENDING) {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}
	}
}
