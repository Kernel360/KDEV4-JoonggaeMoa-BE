package org.silsagusi.api.inquiry.application.mapper;

import org.silsagusi.api.inquiry.application.dto.CreateInquiryAnswerRequest;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InquiryAnswerMapper {

	public InquiryAnswer toEntity(Inquiry inquiry, Agent agent, CreateInquiryAnswerRequest inquiryAnswerRequest) {
		return InquiryAnswer.create(inquiry, agent, inquiryAnswerRequest.getContent());
	}
}
