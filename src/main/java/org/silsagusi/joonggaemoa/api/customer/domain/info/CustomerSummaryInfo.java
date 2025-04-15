package org.silsagusi.joonggaemoa.api.customer.domain.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerSummaryInfo {
    private Long count;
    private Double rate;
}
