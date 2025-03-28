package org.silsagusi.joonggaemoa.domain.consultation.contorller.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class UpdateConsultationRequest {
	private LocalDateTime date;
	private String purpose;
	private String interestProperty;
	private String interestLocation;
	private String contractType;
	private String assetStatus;
	private String memo;
	private String consultationStatus;

}
