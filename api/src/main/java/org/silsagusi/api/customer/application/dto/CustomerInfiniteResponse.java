package org.silsagusi.api.customer.application.dto;

import org.silsagusi.core.domain.customer.entity.Customer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerInfiniteResponse {
	private Long id;
	private String name;
	private String phone;

	public static CustomerInfiniteResponse toResponse(Customer customer) {
		return CustomerInfiniteResponse.builder()
			.id(customer.getId())
			.name(customer.getName())
			.phone(customer.getPhone())
			.build();
	}
}
