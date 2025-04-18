package org.silsagusi.api.consultation.application;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.consultation.application.dto.ConsultationDto;
import org.silsagusi.api.consultation.application.dto.ConsultationMonthResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationSummaryResponse;
import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.api.consultation.infrastructure.ConsultationDataProvider;
import org.silsagusi.api.customer.infrastructure.CustomerDataProvider;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {

	private final ConsultationDataProvider consultationDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final ConsultationMapper consultationMapper;

	public void createConsultation(ConsultationDto.Request consultationRequestDto) {
		Customer customer = customerDataProvider.getCustomer(consultationRequestDto.getCustomerId());

		consultationDataProvider.createConsultation(customer, consultationRequestDto.getDate(),
			Consultation.ConsultationStatus.CONFIRMED);
	}

	public void updateConsultationStatus(Long agentId, Long consultationId, String consultationStatus) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);

		consultationDataProvider.validateAgentAccess(agentId, consultation);

		consultationDataProvider.updateStatus(consultation, consultationStatus);
	}

	public void updateConsultation(
		Long agentId, Long consultationId,
		UpdateConsultationRequest updateConsultationRequest
	) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);

		consultationDataProvider.validateAgentAccess(agentId, consultation);

		UpdateConsultationCommand updateConsultationCommand = consultationMapper.toUpdateConsultationCommand(
			updateConsultationRequest);

		consultationDataProvider.updateConsultation(consultation, updateConsultationCommand);
	}

	public List<ConsultationDto.Response> getAllConsultationsByDate(Long agentId, LocalDateTime date) {

		List<Consultation> consultationList = consultationDataProvider.getConsultationByDate(agentId, date);
		return consultationList.stream().map(consultationMapper::toConsultationResponse).toList();
	}

	public ConsultationDto.Response getConsultation(Long consultationId) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);
		return consultationMapper.toConsultationResponse(consultation);
	}

	public ConsultationMonthResponse getMonthInformation(Long agentId, String date) {

		ConsultationMonthInfo monthInformationCommand = consultationDataProvider.getMonthInformation(agentId, date);
		return consultationMapper.toConsultationMonthResponse(monthInformationCommand);
	}

	public ConsultationSummaryResponse getConsultationSummary(Long agentId) {

		ConsultationSummaryInfo consultationSummaryInfo = consultationDataProvider.getSummary(agentId);

		return consultationMapper.toConsultationSummaryResponse(consultationSummaryInfo);
	}

}
