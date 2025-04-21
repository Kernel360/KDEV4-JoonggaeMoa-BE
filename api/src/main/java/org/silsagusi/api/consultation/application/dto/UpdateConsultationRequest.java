package org.silsagusi.api.consultation.application.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateConsultationRequest {

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime date;
	private String purpose;
	private String interestProperty;
	private String interestLocation;
	private String contractType;
	private String assetStatus;
	private String memo;
}