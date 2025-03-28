package org.silsagusi.joonggaemoa.domain.consultation.contorller.dto;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationCommand;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationResponse {

	private Long consultationId;

	private Long customerId;

	private String customerName;

	private String customerPhone;

	@JsonFormat(pattern = "yyyyMMdd HH:mm")  // JSON 날짜 포맷 지정
	private LocalDateTime date;

	private String purpose;

	private String interestProperty;

	private String interestLocation;

	private String contractType;

	private String assetStatus;

	private String memo;

	private String consultationStatus;

	public static ConsultationResponse of(ConsultationCommand command) {
		return ConsultationResponse.builder()
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
