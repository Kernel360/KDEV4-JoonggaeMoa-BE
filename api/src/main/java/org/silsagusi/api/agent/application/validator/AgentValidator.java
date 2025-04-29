package org.silsagusi.api.agent.application.validator;

import org.silsagusi.api.agent.infrastructure.reposiroty.AgentRepository;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentValidator {

	private final AgentRepository agentRepository;

	public void validateExist(Agent agent) {
		if (agentRepository.existsByUsernameAndDeletedAtIsNull(agent.getUsername())) {
			throw new CustomException(ErrorCode.CONFLICT_USERNAME);
		}

		if (agentRepository.existsByPhoneAndDeletedAtIsNull(agent.getPhone())) {
			throw new CustomException(ErrorCode.CONFLICT_PHONE);
		}

		if (agentRepository.existsByEmailAndDeletedAtIsNull(agent.getEmail())) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}
	}
}
