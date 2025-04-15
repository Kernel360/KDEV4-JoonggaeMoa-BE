package org.silsagusi.joonggaemoa.domain.consultation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultationSummaryResponse {
	private Integer todayCount;
	private Integer remainingCount;
}
