package org.silsagusi.joonggaemoa.domain.consultation.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConsultationMonthInformResponse {
	private Long consultationAll;
	private Long consultationWaiting;
	private Long consultationConfirmed;
	private Long consultationCancelled;
	private Long consultationCompleted;
	private List<Integer> daysCount;

}
