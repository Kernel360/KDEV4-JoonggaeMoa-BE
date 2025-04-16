package org.silsagusi.joonggaemoa.core.domain.customer.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerSummaryInfo {
	private Long count;
	private Double rate;
}
