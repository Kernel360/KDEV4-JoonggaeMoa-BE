package org.silsagusi.core.domain.consultation.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultationSummaryInfo {
	private Long todayCount;
	private Long remainingCount;
}

