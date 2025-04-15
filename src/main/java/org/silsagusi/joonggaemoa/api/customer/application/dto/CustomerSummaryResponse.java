package org.silsagusi.joonggaemoa.api.customer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSummaryResponse {
    private Long count;
    private Double rate;
}
