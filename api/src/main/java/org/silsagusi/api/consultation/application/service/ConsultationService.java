package org.silsagusi.api.consultation.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.consultation.application.dto.ConsultationHistoryResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationMonthResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationSummaryResponse;
import org.silsagusi.api.consultation.application.dto.CreateConsultationRequest;
import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.api.consultation.application.mapper.ConsultationMapper;
import org.silsagusi.api.consultation.application.validator.ConsultationValidator;
import org.silsagusi.api.consultation.infrastructure.dataprovider.ConsultationDataProvider;
import org.silsagusi.api.customer.application.dto.CustomerResponse;
import org.silsagusi.api.customer.infrastructure.dataprovider.CustomerDataProvider;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {

	private final ConsultationDataProvider consultationDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final ConsultationMapper consultationMapper;
	private final ConsultationValidator consultationValidator;

	@Transactional
	public void createConsultation(CreateConsultationRequest createConsultationRequest) {
		Customer customer = customerDataProvider.getCustomer(createConsultationRequest.getCustomerId());
		Consultation consultation = consultationMapper.consultationRequestToEntity(customer, createConsultationRequest);
		consultationDataProvider.createConsultation(consultation);
	}

	@Transactional
	public void updateConsultationStatus(Long agentId, Long consultationId, String consultationStatus) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);
		consultationValidator.validateAgentAccess(agentId, consultation);

		consultationDataProvider.updateStatus(consultation, consultationStatus);
	}

	@Transactional
	public void updateConsultation(
		Long agentId, Long consultationId,
		UpdateConsultationRequest updateConsultationRequest
	) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);
		consultationValidator.validateAgentAccess(agentId, consultation);

		UpdateConsultationCommand updateConsultationCommand = updateConsultationRequest.toCommand();
		consultationDataProvider.updateConsultation(consultation, updateConsultationCommand);
	}

	@Transactional(readOnly = true)
	public ConsultationResponse getConsultation(Long consultationId) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);
		return ConsultationResponse.toResponse(consultation);
	}

	@Transactional(readOnly = true)
	public List<ConsultationResponse> getConsultationsByStatus(Long agentId, String month, String status) {
		List<Consultation> consultations = consultationDataProvider.getConsultationsByStatus(agentId, month, status);

		return consultations.stream().map(ConsultationResponse::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<ConsultationResponse> getAllConsultationsByDate(Long agentId, LocalDateTime date) {
		List<Consultation> consultationList = consultationDataProvider.getConsultationByDate(agentId, date);

		return consultationList.stream().map(ConsultationResponse::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public ConsultationHistoryResponse getConsultationsByCustomer(Long consultationId, Pageable pageable) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);
		Customer customer = consultation.getCustomer();
		Page<Consultation> consultations = consultationDataProvider.getConsultationsByCustomer(customer,
			pageable);

		Page<ConsultationResponse> consultationResponses = consultations.map(
			ConsultationResponse::toResponse);
		CustomerResponse customerResponse = CustomerResponse.toResponse(customer);

		return new ConsultationHistoryResponse(customerResponse, consultationResponses);
	}

	@Transactional(readOnly = true)
	public ConsultationMonthResponse getMonthInformation(Long agentId, String date) {
		ConsultationMonthInfo monthInformationCommand = consultationDataProvider.getMonthInformation(agentId, date);

		return ConsultationMonthResponse.toResponse(monthInformationCommand);
	}

	@Transactional(readOnly = true)
	public ConsultationSummaryResponse getConsultationSummary(Long agentId) {
		ConsultationSummaryInfo consultationSummaryInfo = consultationDataProvider.getSummary(agentId);

		return ConsultationSummaryResponse.toResponse(consultationSummaryInfo);
	}
}