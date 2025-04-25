package org.silsagusi.api.contract.application.validator;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.springframework.stereotype.Component;

@Component
public class ContractValidator {

	public void validateAgentAccess(Long agentId, Contract contract) {
		if (!contract.getCustomerLandlord().getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}
