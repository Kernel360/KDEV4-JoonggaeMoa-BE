package org.silsagusi.core.domain.contract.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractSummaryInfo {
	private Long count;
	private Double rate;
}
