package org.silsagusi.api.customer.application.mapper;

import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.silsagusi.api.customer.application.dto.CustomerExcelDto;
import org.silsagusi.api.survey.application.dto.AnswerDto;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

	public Customer toEntity(CustomerDto.Request customerRequestDto, Agent agent) {
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

	public Customer toEntity(CustomerExcelDto customerExcelDto) {
		return Customer.create(
			customerExcelDto.getName(),
			customerExcelDto.getBirthday(),
			customerExcelDto.getPhone(),
			customerExcelDto.getEmail(),
			customerExcelDto.getJob(),
			customerExcelDto.getIsVip(),
			customerExcelDto.getMemo(),
			customerExcelDto.getConsent(),
			customerExcelDto.getInterestProperty(),
			customerExcelDto.getInterestLocation(),
			customerExcelDto.getAssetStatus(),
			customerExcelDto.getAgent()
		);
	}

	public Customer toEntity(AnswerDto.Request answerRequest, Agent agent) {
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
}
