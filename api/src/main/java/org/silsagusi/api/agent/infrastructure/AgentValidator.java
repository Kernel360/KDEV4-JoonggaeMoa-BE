package org.silsagusi.api.agent.infrastructure;

import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentValidator {

	private final AgentRepository agentRepository;

	public void validateExist(Agent agent) {
		if (agentRepository.existsByUsername(agent.getUsername())) {
			throw new CustomException(ErrorCode.CONFLICT_USERNAME);
		}

		if (agentRepository.existsByPhone(agent.getPhone())) {
			throw new CustomException(ErrorCode.CONFLICT_PHONE);
		}

		if (agentRepository.existsByEmail(agent.getEmail())) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}
	}
}
