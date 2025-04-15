package org.silsagusi.joonggaemoa.api.contract.domain.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractSummaryInfo {
    private Long count;
    private Double rate;
}
