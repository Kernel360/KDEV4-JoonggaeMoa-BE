package org.silsagusi.api.consultation.application;

import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

	public UpdateConsultationCommand toUpdateConsultationCommand(UpdateConsultationRequest updateConsultationRequest) {
		return UpdateConsultationCommand.builder()
			.date(updateConsultationRequest.getDate())
			.purpose(updateConsultationRequest.getPurpose())
			.interestProperty(updateConsultationRequest.getInterestProperty())
			.interestLocation(updateConsultationRequest.getInterestLocation())
			.contractType(updateConsultationRequest.getContractType())
			.assetStatus(updateConsultationRequest.getAssetStatus())
			.memo(updateConsultationRequest.getMemo())
			.consultationStatus(updateConsultationRequest.getConsultationStatus())
			.build();
	}
}
