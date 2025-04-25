package org.silsagusi.api.consultation.application.mapper;

import java.time.LocalDateTime;

import org.silsagusi.api.consultation.application.dto.ConsultationDto;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

	public Consultation consultationRequestToEntity(Customer customer, ConsultationDto.Request consultationRequestDto) {
		Consultation consultation = Consultation.create(
			customer,
			consultationRequestDto.getDate(),
			Consultation.ConsultationStatus.CONFIRMED
		);

		consultation.updateConsultation(consultation.getDate(), consultationRequestDto.getPurpose(),
			consultationRequestDto.getMemo());

		return consultation;
	}

	public Consultation answerRequestToEntity(Customer customer, LocalDateTime date) {
		return Consultation.create(
			customer,
			date,
			Consultation.ConsultationStatus.WAITING
		);
	}
}
