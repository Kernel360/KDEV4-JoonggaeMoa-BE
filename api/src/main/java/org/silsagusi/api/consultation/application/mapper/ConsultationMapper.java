package org.silsagusi.api.consultation.application.mapper;

import java.time.LocalDateTime;

import org.silsagusi.api.consultation.application.dto.ConsultationDto;
import org.silsagusi.api.consultation.application.dto.ConsultationMonthResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationSummaryResponse;
import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;
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

	public UpdateConsultationCommand toUpdateConsultationCommand(UpdateConsultationRequest updateConsultationRequest) {
		return UpdateConsultationCommand.builder()
			.date(updateConsultationRequest.getDate())
			.purpose(updateConsultationRequest.getPurpose())
			.memo(updateConsultationRequest.getMemo())
			.build();
	}

	public ConsultationDto.Response toConsultationResponse(Consultation consultation) {
		return ConsultationDto.Response.builder()
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

	public ConsultationMonthResponse toConsultationMonthResponse(ConsultationMonthInfo info) {
		return ConsultationMonthResponse.builder()
			.consultationAll(info.getConsultationAll())
			.consultationWaiting(info.getConsultationWaiting())
			.consultationConfirmed(info.getConsultationConfirmed())
			.consultationCancelled(info.getConsultationCancelled())
			.consultationCompleted(info.getConsultationCompleted())
			.daysCount(info.getDaysCount())
			.build();
	}

	public ConsultationSummaryResponse toConsultationSummaryResponse(ConsultationSummaryInfo info) {
		return ConsultationSummaryResponse.builder()
			.todayCount(info.getTodayCount())
			.remainingCount(info.getRemainingCount())
			.build();
	}
}
