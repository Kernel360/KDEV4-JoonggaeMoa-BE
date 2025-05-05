package org.silsagusi.api.customer.application.dto;

import java.time.LocalDate;

import org.silsagusi.core.domain.customer.entity.Customer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponse {
	private Long id;
	private String name;
	private LocalDate birthday;
	private String phone;
	private String email;
	private String job;
	private Boolean isVip;

	public static CustomerResponse toResponse(Customer customer) {
		return CustomerResponse.builder()
			.id(customer.getId())
			.name(customer.getName())
			.birthday(customer.getBirthday())
			.phone(customer.getPhone())
			.email(customer.getEmail())
			.job(customer.getJob())
			.isVip(customer.getIsVip())
			.build();
	}
}
