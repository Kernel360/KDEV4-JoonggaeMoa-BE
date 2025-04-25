package org.silsagusi.api.consultation.application.dto;

import java.util.List;

import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultationMonthResponse {
	private Long consultationAll;
	private Long consultationWaiting;
	private Long consultationConfirmed;
	private Long consultationCancelled;
	private Long consultationCompleted;
	private List<Integer> daysCount;

	public static ConsultationMonthResponse toResponse(ConsultationMonthInfo info) {
		return ConsultationMonthResponse.builder()
			.consultationAll(info.getConsultationAll())
			.consultationWaiting(info.getConsultationWaiting())
			.consultationConfirmed(info.getConsultationConfirmed())
			.consultationCancelled(info.getConsultationCancelled())
			.consultationCompleted(info.getConsultationCompleted())
			.daysCount(info.getDaysCount())
			.build();
	}
}
