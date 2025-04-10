package org.silsagusi.joonggaemoa.domain.consultation.controller.dto;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationCommand;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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

		public static Response of(ConsultationCommand command) {
			return Response.builder()
				.consultationId(command.getConsultationId())
				.customerId(command.getCustomerId())
				.customerName(command.getCustomerName())
				.customerPhone(command.getCustomerPhone())
				.date(command.getDate())
				.purpose(command.getPurpose())
				.interestProperty(command.getInterestProperty())
				.interestLocation(command.getInterestLocation())
				.contractType(command.getContractType())
				.assetStatus(command.getAssetStatus())
				.memo(command.getMemo())
				.consultationStatus(command.getConsultationStatus())
				.build();
		}
	}
}
