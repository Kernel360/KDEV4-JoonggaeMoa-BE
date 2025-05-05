package org.silsagusi.api.consultation.application.validator;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
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
