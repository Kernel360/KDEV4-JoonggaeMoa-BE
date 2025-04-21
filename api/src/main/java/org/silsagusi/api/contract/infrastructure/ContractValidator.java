package org.silsagusi.api.contract.infrastructure;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;
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
