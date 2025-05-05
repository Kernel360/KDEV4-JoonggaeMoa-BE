package org.silsagusi.api.consultation.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultationHistoryResponse {
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String customerEmail;
	private String customerJob;
	private String interestProperty;
	private String interestLocation;
	private String assetStatus;
	private Page<ConsultationDetail> consultations;

	@Getter
	@Builder
	public static class ConsultationDetail {
		private Long consultationId;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  // JSON 날짜 포맷 지정
		private LocalDateTime date;
		private String purpose;
		private String memo;
		private String consultationStatus;

		public static Page<ConsultationDetail> toResponse(Page<Consultation> consultations) {
			return consultations.map(
				consultation -> ConsultationDetail.builder()
					.consultationId(consultation.getId())
					.date(consultation.getDate())
					.purpose(consultation.getPurpose())
					.memo(consultation.getMemo())
					.consultationStatus(consultation.getConsultationStatus() + "")
					.build()
			);
		}
	}

	public static ConsultationHistoryResponse toResponse(Customer customer, Page<Consultation> consultations) {
		return ConsultationHistoryResponse.builder()
			.customerId(customer.getId())
			.customerName(customer.getName())
			.customerPhone(customer.getPhone())
			.customerEmail(customer.getEmail())
			.customerJob(customer.getJob())
			.interestProperty(customer.getInterestProperty())
			.interestLocation(customer.getInterestLocation())
			.assetStatus(customer.getAssetStatus())
			.consultations(ConsultationDetail.toResponse(consultations))
			.build();
	}
}
