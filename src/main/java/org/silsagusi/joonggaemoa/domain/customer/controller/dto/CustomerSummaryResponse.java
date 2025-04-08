package org.silsagusi.joonggaemoa.domain.customer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSummaryResponse {
	private Long count;
	private Double rate;
}
