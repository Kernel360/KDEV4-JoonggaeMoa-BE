package org.silsagusi.api.customer.application.dto;

import java.time.LocalDate;

import org.silsagusi.core.domain.agent.Agent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerExcelRequest {
	private String name;
	private LocalDate birthday;
	private String phone;
	private String email;
	private String job;
	private Boolean isVip;
	private String memo;
	private Boolean consent;
	private String interestProperty;
	private String interestLocation;
	private String assetStatus;
	private Agent agent;

	public static CustomerExcelRequest create(String name, LocalDate birthday, String phone, String email, String job,
		Boolean isVip, String memo, Boolean consent, String interestProperty, String interestLocation,
		String assetStatus, Agent agent) {
		return CustomerExcelRequest.builder()
			.name(name)
			.birthday(birthday)
			.phone(phone)
			.email(email)
			.job(job)
			.isVip(isVip)
			.memo(memo)
			.consent(consent)
			.interestProperty(interestProperty)
			.interestLocation(interestLocation)
			.assetStatus(assetStatus)
			.agent(agent)
			.build();
	}
}
