package org.silsagusi.core.domain.consultation.command;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateConsultationCommand {
	private LocalDateTime date;
	private String purpose;
	private String interestProperty;
	private String interestLocation;
	private String contractType;
	private String assetStatus;
	private String memo;
	private String consultationStatus;
}
