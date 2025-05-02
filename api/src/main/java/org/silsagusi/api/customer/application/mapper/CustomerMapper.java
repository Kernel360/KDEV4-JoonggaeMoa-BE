package org.silsagusi.api.customer.application.mapper;

import org.silsagusi.api.customer.application.dto.CreateCustomerRequest;
import org.silsagusi.api.customer.application.dto.CustomerExcelRequest;
import org.silsagusi.api.inquiry.application.dto.CreateConsultationRequest;
import org.silsagusi.api.survey.application.dto.SubmitAnswerRequest;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

	public Customer toEntity(CreateCustomerRequest customerRequestDto, Agent agent) {
		return Customer.create(
			customerRequestDto.getName(),
			customerRequestDto.getBirthday(),
			customerRequestDto.getPhone(),
			customerRequestDto.getEmail(),
			customerRequestDto.getJob(),
			customerRequestDto.getIsVip(),
			customerRequestDto.getMemo(),
			customerRequestDto.getConsent(),
			customerRequestDto.getInterestProperty(),
			customerRequestDto.getInterestLocation(),
			customerRequestDto.getAssetStatus(),
			agent
		);
	}

	public Customer toEntity(CustomerExcelRequest customerExcelRequest) {
		return Customer.create(
			customerExcelRequest.getName(),
			customerExcelRequest.getBirthday(),
			customerExcelRequest.getPhone(),
			customerExcelRequest.getEmail(),
			customerExcelRequest.getJob(),
			customerExcelRequest.getIsVip(),
			customerExcelRequest.getMemo(),
			customerExcelRequest.getConsent(),
			customerExcelRequest.getInterestProperty(),
			customerExcelRequest.getInterestLocation(),
			customerExcelRequest.getAssetStatus(),
			customerExcelRequest.getAgent()
		);
	}

	public Customer toEntity(SubmitAnswerRequest answerRequest, Agent agent) {
		return Customer.create(
			answerRequest.getName(),
			null,
			answerRequest.getPhone(),
			answerRequest.getEmail(),
			null,
			null,
			null,
			answerRequest.getConsent(),
			null,
			null,
			null,
			agent
		);
	}

	public Customer toEntity(CreateConsultationRequest createConsultationRequest, Agent agent) {
		return Customer.create(
			createConsultationRequest.getName(),
			null,
			createConsultationRequest.getPhone(),
			createConsultationRequest.getEmail(),
			null,
			null,
			null,
			createConsultationRequest.getConsent(),
			null,
			null,
			null,
			agent
		);
	}
}
