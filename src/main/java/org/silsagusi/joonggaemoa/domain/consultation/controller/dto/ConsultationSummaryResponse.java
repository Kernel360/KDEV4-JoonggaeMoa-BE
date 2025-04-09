package org.silsagusi.joonggaemoa.domain.consultation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultationSummaryResponse {
	private Integer todayCount;
	private Integer remainingCount;
}
