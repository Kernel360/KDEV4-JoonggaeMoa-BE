package org.silsagusi.joonggaemoa.domain.consultation.service.command;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultationCommand {
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

	public static ConsultationCommand of(Consultation consultation) {
		return ConsultationCommand.builder()
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
			.consultationStatus(String.valueOf(consultation.getConsultationStatus()))
			.build();
	}
}
