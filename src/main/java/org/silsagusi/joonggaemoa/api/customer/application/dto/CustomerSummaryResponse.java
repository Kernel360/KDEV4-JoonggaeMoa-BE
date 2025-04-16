package org.silsagusi.joonggaemoa.api.customer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.silsagusi.joonggaemoa.core.domain.customer.info.CustomerSummaryInfo;

@Getter
@Builder
@AllArgsConstructor
public class CustomerSummaryResponse {
	private Long count;
	private Double rate;

	public static CustomerSummaryResponse of(CustomerSummaryInfo info) {
		return CustomerSummaryResponse.builder()
			.count(info.getCount())
			.rate(info.getRate())
			.build();
	}
}
