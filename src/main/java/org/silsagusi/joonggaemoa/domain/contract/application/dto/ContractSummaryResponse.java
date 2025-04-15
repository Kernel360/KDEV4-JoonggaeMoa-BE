package org.silsagusi.joonggaemoa.domain.contract.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContractSummaryResponse {

	private Long count;
	private Double rate;
}
