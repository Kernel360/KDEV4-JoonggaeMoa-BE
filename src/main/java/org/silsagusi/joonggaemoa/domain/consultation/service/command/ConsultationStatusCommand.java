package org.silsagusi.joonggaemoa.domain.consultation.service.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConsultationStatusCommand {
	private Long consultationAll;
	private Long consultationWaiting;
	private Long consultationConfirmed;
	private Long consultationCancelled;
	private Long consultationCompleted;
}
