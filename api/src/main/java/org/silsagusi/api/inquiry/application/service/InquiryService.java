package org.silsagusi.api.inquiry.application.service;

import java.time.format.DateTimeFormatter;

import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.consultation.application.mapper.ConsultationMapper;
import org.silsagusi.api.consultation.infrastructure.dataprovider.ConsultationDataProvider;
import org.silsagusi.api.customer.application.mapper.CustomerMapper;
import org.silsagusi.api.customer.infrastructure.dataprovider.CustomerDataProvider;
import org.silsagusi.api.inquiry.application.dto.Chat;
import org.silsagusi.api.inquiry.application.dto.CreateConsultationRequest;
import org.silsagusi.api.inquiry.application.dto.CreateInquiryAnswerRequest;
import org.silsagusi.api.inquiry.application.dto.CreateInquiryRequest;
import org.silsagusi.api.inquiry.application.dto.DeleteInquiryRequest;
import org.silsagusi.api.inquiry.application.dto.InquiryResponse;
import org.silsagusi.api.inquiry.application.dto.UpdateInquiryRequest;
import org.silsagusi.api.inquiry.application.mapper.InquiryAnswerMapper;
import org.silsagusi.api.inquiry.application.mapper.InquiryMapper;
import org.silsagusi.api.inquiry.application.validator.InquiryValidator;
import org.silsagusi.api.inquiry.infrastructure.dataprovider.InquiryDataProvider;
import org.silsagusi.api.inquiry.infrastructure.external.GeminiApiClient;
import org.silsagusi.api.notification.infrastructure.dataprovider.NotificationDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.inquiry.command.UpdateInquiryCommand;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InquiryService {

	private final InquiryDataProvider inquiryDataProvider;
	private final InquiryMapper inquiryMapper;
	private final InquiryAnswerMapper inquiryAnswerMapper;
	private final InquiryValidator inquiryValidator;
	private final AgentDataProvider agentDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final CustomerMapper customerMapper;
	private final ConsultationMapper consultationMapper;
	private final ConsultationDataProvider consultationDataProvider;
	private final NotificationDataProvider notificationDataProvider;
	private final GeminiApiClient geminiApiClient;

	@Transactional
	public void createInquiry(CreateInquiryRequest inquiryCreateRequest) {
		Inquiry inquiry = inquiryMapper.toEntity(inquiryCreateRequest);
		inquiryDataProvider.createInquiry(inquiry);
	}

	@Transactional
	public void updateInquiry(Long inquiryId, UpdateInquiryRequest inquiryUpdateRequest) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		inquiryValidator.validatePassword(inquiry, inquiryUpdateRequest.getPassword());
		UpdateInquiryCommand updateInquiryCommand = inquiryUpdateRequest.toCommand();
		inquiryDataProvider.updateInquiry(inquiry, updateInquiryCommand);
	}

	@Transactional
	public void deleteInquiry(Long inquiryId, DeleteInquiryRequest deleteInquiryRequest) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		inquiryValidator.validatePassword(inquiry, deleteInquiryRequest.getPassword());
		inquiryDataProvider.deleteInquiry(inquiry);
	}

	@Transactional(readOnly = true)
	public Page<InquiryResponse> getAllInquiry(Pageable pageable) {
		Page<Inquiry> inquiries = inquiryDataProvider.getInquiries(pageable);
		return inquiries.map(InquiryResponse::toResponse);
	}

	@Transactional(readOnly = true)
	public InquiryResponse getInquiry(Long inquiryId) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		return InquiryResponse.toResponse(inquiry);
	}

	@Transactional
	public void createInquiryAnswer(Long agentId, Long inquiryId, CreateInquiryAnswerRequest inquiryAnswerRequest) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		Agent agent = agentDataProvider.getAgentById(agentId);
		inquiryDataProvider.markAsAnswered(inquiry);
		InquiryAnswer inquiryAnswer = inquiryAnswerMapper.toEntity(inquiry, agent, inquiryAnswerRequest);
		inquiryDataProvider.createInquiryAnswer(inquiryAnswer);
	}

	@Transactional
	public void createConsultation(CreateConsultationRequest createConsultationRequest) {
		Agent agent = agentDataProvider.getAgentById(createConsultationRequest.getAgentId());
		Customer customer = customerDataProvider.getCustomerByPhone(createConsultationRequest.getPhone());
		if (customer == null) {
			customer = customerMapper.toEntity(createConsultationRequest, agent);
			customerDataProvider.createCustomer(customer);
		}

		Consultation consultation = consultationMapper.answerRequestToEntity(customer,
			createConsultationRequest.getConsultAt());
		consultationDataProvider.createConsultation(consultation);

		// 상담 신청 시 알림
		notificationDataProvider.notify(
			agent.getId(),
			NotificationType.CONSULTATION,
			customer.getName() + "님이 [" + consultation.getDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))
				+ "] 시에 상담을 신청했습니다."
		);
	}

	public Chat.Response chat(Chat.Request chatRequest) {
		String answer = geminiApiClient.askGemini(chatRequest.getQuestion());
		return new Chat.Response(answer);
	}
}
