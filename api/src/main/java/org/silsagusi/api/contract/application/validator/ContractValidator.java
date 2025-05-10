package org.silsagusi.api.contract.application.validator;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.springframework.stereotype.Component;

@Component
public class ContractValidator {

	public void validateAgentAccess(Long agentId, Contract contract) {
		if (!contract.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}
