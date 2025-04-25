package org.silsagusi.api.inquiry.application.service;

import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.inquiry.application.dto.InquiryAnswerDto;
import org.silsagusi.api.inquiry.application.dto.InquiryDto;
import org.silsagusi.api.inquiry.application.mapper.InquiryAnswerMapper;
import org.silsagusi.api.inquiry.application.mapper.InquiryMapper;
import org.silsagusi.api.inquiry.application.validator.InquiryValidator;
import org.silsagusi.api.inquiry.infrastructure.dataprovider.InquiryDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.inquiry.command.UpdateInquiryCommand;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;
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

	@Transactional
	public void createInquiry(InquiryDto.CreateRequest inquiryCreateRequest) {
		Inquiry inquiry = inquiryMapper.toEntity(inquiryCreateRequest);
		inquiryDataProvider.createInquiry(inquiry);
	}

	@Transactional
	public void updateInquiry(Long inquiryId, InquiryDto.UpdateRequest inquiryUpdateRequest) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		inquiryValidator.validatePassword(inquiry, inquiryUpdateRequest.getPassword());
		UpdateInquiryCommand updateInquiryCommand = InquiryDto.toCommand(inquiryUpdateRequest);
		inquiryDataProvider.updateInquiry(inquiry, updateInquiryCommand);
	}

	@Transactional
	public void deleteInquiry(Long inquiryId, InquiryDto.PasswordRequest passwordRequest) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		inquiryValidator.validatePassword(inquiry, passwordRequest.getPassword());
		inquiryDataProvider.deleteInquiry(inquiry);
	}

	@Transactional(readOnly = true)
	public Page<InquiryDto.Response> getAllInquiry(Pageable pageable) {
		Page<Inquiry> inquiries = inquiryDataProvider.getInquiries(pageable);
		return inquiries.map(InquiryDto::toResponse);
	}

	@Transactional(readOnly = true)
	public InquiryDto.Response getInquiry(Long inquiryId) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		return InquiryDto.toResponse(inquiry);
	}

	@Transactional
	public void createInquiryAnswer(Long agentId, Long inquiryId, InquiryAnswerDto.Request inquiryAnswerRequest) {
		Inquiry inquiry = inquiryDataProvider.getInquiry(inquiryId);
		Agent agent = agentDataProvider.getAgentById(agentId);
		inquiryDataProvider.markAsAnswered(inquiry);
		InquiryAnswer inquiryAnswer = inquiryAnswerMapper.toEntity(inquiry, agent, inquiryAnswerRequest);
		inquiryDataProvider.createInquiryAnswer(inquiryAnswer);
	}

}
