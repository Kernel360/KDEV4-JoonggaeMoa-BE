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

	private Long customerId;

	private String customerName;

	private String customerPhone;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  // JSON 날짜 포맷 지정
	private LocalDateTime date;

	private String purpose;

	private String memo;

	private String consultationStatus;

	public static ConsultationResponse toResponse(Consultation consultation) {
		return ConsultationResponse.builder()
			.consultationId(consultation.getId())
			.customerId(consultation.getCustomer().getId())
			.customerName(consultation.getCustomer().getName())
			.customerPhone(consultation.getCustomer().getPhone())
			.date(consultation.getDate())
			.purpose(consultation.getPurpose())
			.memo(consultation.getMemo())
			.consultationStatus(consultation.getConsultationStatus().toString())
			.build();
	}
}
