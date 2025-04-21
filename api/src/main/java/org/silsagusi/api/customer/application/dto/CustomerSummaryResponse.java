package org.silsagusi.api.customer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomerSummaryResponse {
	private Long count;
	private Double rate;
}
