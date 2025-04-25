package org.silsagusi.api.customer.application.dto;

import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerSummaryResponse {
	private Long count;
	private Double rate;

	public static CustomerSummaryResponse toResponse(CustomerSummaryInfo info) {
		return CustomerSummaryResponse.builder()
			.count(info.getCount())
			.rate(info.getRate())
			.build();
	}
}
