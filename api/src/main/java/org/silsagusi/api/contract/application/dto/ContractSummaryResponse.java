package org.silsagusi.api.contract.application.dto;

import org.silsagusi.core.domain.contract.info.ContractSummaryInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ContractSummaryResponse {
	private Long count;
	private Double rate;

	public static ContractSummaryResponse toResponse(ContractSummaryInfo info) {
		return ContractSummaryResponse.builder()
			.count(info.getCount())
			.rate(info.getRate())
			.build();
	}
}
