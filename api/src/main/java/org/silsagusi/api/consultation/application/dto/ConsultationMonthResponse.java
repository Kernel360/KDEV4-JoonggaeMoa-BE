package org.silsagusi.api.consultation.application.dto;

import java.util.List;

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
}
