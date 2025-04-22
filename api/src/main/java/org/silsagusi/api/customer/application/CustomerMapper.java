package org.silsagusi.api.customer.application;

import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.silsagusi.api.customer.application.dto.CustomerSummaryResponse;
import org.silsagusi.api.survey.application.dto.AnswerDto;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.command.UpdateCustomerCommand;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;
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

	public UpdateCustomerCommand toUpdateCustomerCommand(Customer customer, CustomerDto.Request customerRequestDto) {
		return UpdateCustomerCommand.builder()
			.customer(customer)
			.name(customerRequestDto.getName())
			.birthday(customerRequestDto.getBirthday())
			.phone(customerRequestDto.getPhone())
			.email(customerRequestDto.getEmail())
			.job(customerRequestDto.getJob())
			.isVip(customerRequestDto.getIsVip())
			.memo(customerRequestDto.getMemo())
			.consent(customerRequestDto.getConsent())
			.interestProperty(customerRequestDto.getInterestProperty())
			.interestLocation(customerRequestDto.getInterestLocation())
			.assetStatus(customerRequestDto.getAssetStatus())
			.build();
	}

	public Customer answerDtoToCustomer(AnswerDto.Request answerRequest, Agent agent) {
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

	public CustomerDto.Response toCustomerResponse(Customer customer) {
		return CustomerDto.Response.builder()
			.id(customer.getId())
			.name(customer.getName())
			.birthday(customer.getBirthday())
			.phone(customer.getPhone())
			.email(customer.getEmail())
			.job(customer.getJob())
			.isVip(customer.getIsVip())
			.memo(customer.getMemo())
			.consent(customer.getConsent())
			.interestProperty(customer.getInterestProperty())
			.interestLocation(customer.getInterestLocation())
			.assetStatus(customer.getAssetStatus())
			.build();
	}

	public CustomerSummaryResponse toCustomerSummaryResponse(CustomerSummaryInfo info) {
		return CustomerSummaryResponse.builder()
			.count(info.getCount())
			.rate(info.getRate())
			.build();
	}
}
