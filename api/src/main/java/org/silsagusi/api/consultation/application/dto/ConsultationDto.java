package org.silsagusi.api.consultation.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ConsultationDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "ConsultationRequestDto")
	public static class Request {

		@NotNull
		private Long customerId;

		@NotNull
		@Future
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		private LocalDateTime date;
	}

	@Getter
	@Builder
	public static class Response {
		private Long consultationId;

		private Long customerId;

		private String customerName;

		private String customerPhone;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  // JSON 날짜 포맷 지정
		private LocalDateTime date;

		private String purpose;

		private String interestProperty;

		private String interestLocation;

		private String contractType;

		private String assetStatus;

		private String memo;

		private String consultationStatus;

		public static Response of(Consultation consultation) {
			return Response.builder()
				.consultationId(consultation.getId())
				.customerId(consultation.getCustomer().getId())
				.customerName(consultation.getCustomer().getName())
				.customerPhone(consultation.getCustomer().getPhone())
				.date(consultation.getDate())
				.purpose(consultation.getPurpose())
				.interestProperty(consultation.getInterestProperty())
				.interestLocation(consultation.getInterestLocation())
				.contractType(consultation.getContractType())
				.assetStatus(consultation.getAssetStatus())
				.memo(consultation.getMemo())
				.consultationStatus(consultation.getConsultationStatus().toString())
				.build();
		}
	}
}
