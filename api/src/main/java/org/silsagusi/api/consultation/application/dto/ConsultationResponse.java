package org.silsagusi.api.consultation.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.consultation.entity.Consultation;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultationResponse {
	private Long consultationId;
	private String customerName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  // JSON 날짜 포맷 지정
	private LocalDateTime date;
	private String consultationStatus;

	public static ConsultationResponse toResponse(Consultation consultation) {
		return ConsultationResponse.builder()
			.consultationId(consultation.getId())
			.customerName(consultation.getCustomer().getName())
			.date(consultation.getDate())
			.consultationStatus(consultation.getConsultationStatus().toString())
			.build();
	}
}
