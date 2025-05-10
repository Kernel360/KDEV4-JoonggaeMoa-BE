package org.silsagusi.api.consultation.application.mapper;

import java.time.LocalDateTime;

import org.silsagusi.api.consultation.application.dto.CreateConsultationRequest;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.enums.ConsultationStatus;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

	public Consultation consultationRequestToEntity(Agent agent, Customer customer,
		CreateConsultationRequest consultationRequestDto) {
		return Consultation.create(
			agent,
			customer,
			consultationRequestDto.getDate(),
			consultationRequestDto.getPurpose(),
			consultationRequestDto.getMemo(),
			ConsultationStatus.CONFIRMED
		);
	}

	public Consultation answerRequestToEntity(Agent agent, Customer customer, LocalDateTime date) {
		return Consultation.create(
			agent,
			customer,
			date,
			null,
			null,
			ConsultationStatus.WAITING
		);
	}
}
