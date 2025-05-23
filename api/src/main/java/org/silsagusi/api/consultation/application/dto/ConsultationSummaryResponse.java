package org.silsagusi.api.consultation.application.dto;

import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultationSummaryResponse {
	private Long todayCount;
	private Long remainingCount;

	public static ConsultationSummaryResponse toResponse(ConsultationSummaryInfo info) {
		return ConsultationSummaryResponse.builder()
			.todayCount(info.getTodayCount())
			.remainingCount(info.getRemainingCount())
			.build();
	}
}
