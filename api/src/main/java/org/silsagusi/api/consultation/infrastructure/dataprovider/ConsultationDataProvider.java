package org.silsagusi.api.consultation.infrastructure.dataprovider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.consultation.infrastructure.repository.ConsultationRepository;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.enums.ConsultationStatus;
import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsultationDataProvider {

	private final ConsultationRepository consultationRepository;

	public void createConsultation(Consultation consultation) {
		consultationRepository.save(consultation);
	}

	public Consultation getConsultation(Long consultationId) {
		return consultationRepository.findByIdAndDeletedAtIsNull(consultationId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public List<Consultation> getConsultationsByStatus(Long agentId, String month, String status) {
		YearMonth yearMonth = YearMonth.parse(month);
		LocalDateTime startOfMonth = yearMonth.atDay(1).atTime(LocalTime.MIN);
		LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

		return consultationRepository.findAllByAgent_IdAndDateBetweenAndConsultationStatusAndDeletedAtIsNull(
			agentId, startOfMonth, endOfMonth, ConsultationStatus.valueOf(status));
	}

	public Page<Consultation> getConsultationsByCustomer(Customer customer, Pageable pageable) {
		return consultationRepository.findByCustomerAndDeletedAtIsNull(customer, pageable);
	}

	public void updateStatus(Consultation consultation, String consultationStatus) {
		consultation.updateStatus(ConsultationStatus.valueOf(consultationStatus));
	}

	public void updateConsultation(Consultation consultation, UpdateConsultationCommand updateConsultationCommand) {
		consultation.updateConsultation(updateConsultationCommand.getDate(), updateConsultationCommand.getPurpose(),
			updateConsultationCommand.getMemo());
	}

	public List<Consultation> getConsultationByDate(Long agentId, LocalDateTime date) {
		LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
		LocalDateTime endOfDay = date.toLocalDate().atTime(LocalTime.MAX);
		return consultationRepository.findAllByAgent_IdAndDateBetweenAndDeletedAtIsNull(agentId, startOfDay, endOfDay);
	}

	public ConsultationMonthInfo getMonthInformation(Long agentId, String date) {
		YearMonth yearMonth = YearMonth.parse(date);
		LocalDateTime startOfMonth = yearMonth.atDay(1).atTime(LocalTime.MIN);
		LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
		int days = yearMonth.lengthOfMonth();

		List<Consultation> consultations =
			consultationRepository.findAllByAgent_IdAndDateBetweenAndDeletedAtIsNull(agentId, startOfMonth, endOfMonth);

		Map<ConsultationStatus, Long> statusCountMap = consultations.stream()
			.collect(Collectors.groupingBy(Consultation::getConsultationStatus, Collectors.counting()));

		// date count information
		List<Integer> dailyCount = new ArrayList<>(Collections.nCopies(days, 0));

		for (int i = 0; i < days; i++) {
			LocalDate localDate = yearMonth.atDay(i + 1);

			LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);
			LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
			dailyCount.set(i,
				consultationRepository.countByAgent_IdAndDateBetweenAndDeletedAtIsNull(agentId, startOfDay, endOfDay));
		}

		return ConsultationMonthInfo.builder()
			.consultationAll((long)consultations.size())
			.consultationWaiting(statusCountMap.getOrDefault(ConsultationStatus.WAITING, 0L))
			.consultationConfirmed(statusCountMap.getOrDefault(ConsultationStatus.CONFIRMED, 0L))
			.consultationCancelled(statusCountMap.getOrDefault(ConsultationStatus.CANCELED, 0L))
			.consultationCompleted(statusCountMap.getOrDefault(ConsultationStatus.COMPLETED, 0L))
			.daysCount(dailyCount)
			.build();
	}

	public ConsultationSummaryInfo getSummary(Long agentId) {
		LocalDate today = LocalDate.now();

		long total = consultationRepository.countTodayConsultations(agentId, today);
		long remain = consultationRepository.countTodayRemaining(agentId, today);

		return new ConsultationSummaryInfo(total, remain);
	}
}
