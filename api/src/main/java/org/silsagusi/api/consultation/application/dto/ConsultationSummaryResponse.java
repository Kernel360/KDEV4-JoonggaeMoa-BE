package org.silsagusi.api.consultation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ConsultationSummaryResponse {
	private Integer todayCount;
	private Integer remainingCount;
}
