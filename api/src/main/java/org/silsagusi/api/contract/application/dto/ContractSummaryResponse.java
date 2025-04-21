package org.silsagusi.api.contract.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ContractSummaryResponse {
	private Long count;
	private Double rate;
}
