package org.silsagusi.api.consultation.application;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.consultation.application.dto.ConsultationDto;
import org.silsagusi.api.consultation.application.dto.ConsultationHistoryDto;
import org.silsagusi.api.consultation.application.dto.ConsultationMonthResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationSummaryResponse;
import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.api.consultation.infrastructure.ConsultationDataProvider;
import org.silsagusi.api.consultation.infrastructure.ConsultationValidator;
import org.silsagusi.api.customer.application.CustomerMapper;
import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.silsagusi.api.customer.infrastructure.CustomerDataProvider;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {

	private final ConsultationDataProvider consultationDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final ConsultationMapper consultationMapper;
	private final ConsultationValidator consultationValidator;
	private final CustomerMapper customerMapper;

	public void createConsultation(ConsultationDto.Request consultationRequestDto) {
		Customer customer = customerDataProvider.getCustomer(consultationRequestDto.getCustomerId());

		Consultation consultation = consultationMapper.consultationRequestToEntity(customer, consultationRequestDto);

		consultationDataProvider.createConsultation(consultation);
	}

	public void updateConsultationStatus(Long agentId, Long consultationId, String consultationStatus) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);

		consultationValidator.validateAgentAccess(agentId, consultation);

		consultationDataProvider.updateStatus(consultation, consultationStatus);
	}

	public void updateConsultation(
		Long agentId, Long consultationId,
		UpdateConsultationRequest updateConsultationRequest
	) {
		Consultation consultation = consultationDataProvider.getConsultation(consultationId);

		consultationValidator.validateAgentAccess(agentId, consultation);

		UpdateConsultationCommand updateConsultationCommand = consultationMapper.toUpdateConsultationCommand(
			updateConsultationRequest);

		consultationDataProvider.updateConsultation(consultation, updateConsultationCommand);
	}

	public List<ConsultationDto.Response> getAllConsultationsByDate(Long agentId, LocalDateTime date) {

		List<Consultation> consultationList = consultationDataProvider.getConsultationByDate(agentId, date);
		return consultationList.stream().map(consultationMapper::toConsultationResponse).toList();
	}

	public ConsultationHistoryDto getConsultationsByCustomer(Long consultationId, Pageable pageable) {
		Customer customer = customerDataProvider.getCustomer(consultationId);
		Page<Consultation> consultations = consultationDataProvider.getConsultationsByCustomer(customer,
			pageable);

		Page<ConsultationDto.Response> consultationResponses = consultations.map(
			ConsultationDto.Response::fromConsultation);
		CustomerDto.Response customerResponse = customerMapper.toCustomerResponse(customer);

		return new ConsultationHistoryDto(customerResponse, consultationResponses);
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