package org.silsagusi.joonggaemoa.api.contract.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.silsagusi.joonggaemoa.core.domain.contract.info.ContractSummaryInfo;

@Builder
@Getter
@AllArgsConstructor
public class ContractSummaryResponse {

	private Long count;
	private Double rate;

	public static ContractSummaryResponse of(ContractSummaryInfo info) {
		return ContractSummaryResponse.builder()
			.count(info.getCount())
			.rate(info.getRate())
			.build();
	}
}
