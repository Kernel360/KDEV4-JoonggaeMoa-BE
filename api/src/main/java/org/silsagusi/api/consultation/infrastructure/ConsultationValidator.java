package org.silsagusi.api.consultation.infrastructure;

import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.springframework.stereotype.Component;

@Component
public class ConsultationValidator {

	public void validateAgentAccess(Long agentId, Consultation consultation) {
		if (!consultation.getCustomer().getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}
