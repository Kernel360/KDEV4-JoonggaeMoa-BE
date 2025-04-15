package org.silsagusi.joonggaemoa.api.consultation.infrastructure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.silsagusi.joonggaemoa.api.consultation.domain.dataProvider.ConsultationDataProvider;
import org.silsagusi.joonggaemoa.api.consultation.domain.entity.Consultation;
import org.silsagusi.joonggaemoa.api.consultation.domain.entity.Consultation.ConsultationStatus;
import org.silsagusi.joonggaemoa.api.consultation.domain.info.ConsultationMonthInfo;
import org.silsagusi.joonggaemoa.api.consultation.domain.info.ConsultationSummaryInfo;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsultationDataProviderImpl implements ConsultationDataProvider {

	private final ConsultationRepository consultationRepository;

	@Override
	public void createConsultation(Customer customer, LocalDateTime consultationDate,
		ConsultationStatus consultationStatus) {

		Consultation consultation = new Consultation(
			customer,
			consultationDate,
			ConsultationStatus.CONFIRMED
		);

		consultationRepository.save(consultation);
	}

	@Override
	public Consultation getConsultation(Long consultationId) {
		Consultation consultation = consultationRepository.findById(consultationId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return consultation;
	}

	@Override
	public void validateAgentAccess(Long agentId, Consultation consultation) {
		if (!consultation.getCustomer().getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}

	@Override
	public void updateStatus(Consultation consultation, String consultationStatus) {
		consultation.updateStatus(Consultation.ConsultationStatus.valueOf(consultationStatus));
		consultationRepository.save(consultation);
	}

	@Override
	public void updateConcsultation(
		Consultation consultation, LocalDateTime date, String purpose, String interestProperty, String interestLocation,
		String contractType, String assetStatus, String memo, String consultationStatus
	) {

		consultation.updateConsultation(
			(date == null) ? consultation.getDate() : date,
			(purpose == null || purpose.isBlank()) ? consultation.getPurpose() : purpose,
			(interestProperty == null || interestProperty.isBlank()) ? consultation.getInterestProperty() :
				interestProperty,
			(interestLocation == null || interestLocation.isBlank()) ? consultation.getInterestLocation() :
				interestLocation,
			(contractType == null || contractType.isBlank()) ? consultation.getContractType() : contractType,
			(assetStatus == null || assetStatus.isBlank()) ? consultation.getAssetStatus() : assetStatus,
			(memo == null || memo.isBlank()) ? consultation.getMemo() : memo,
			(consultationStatus == null) ? consultation.getConsultationStatus() :
				ConsultationStatus.valueOf(consultationStatus)
		);
		consultationRepository.save(consultation);
	}

	@Override
	public List<Consultation> getConsultationByDate(Long agentId, LocalDateTime date) {
		LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
		LocalDateTime endOfDay = date.toLocalDate().atTime(LocalTime.MAX);

		List<Consultation> consultationList = consultationRepository.findAllByCustomer_AgentIdAndDateBetween(agentId,
			startOfDay, endOfDay);
		return consultationList;
	}

	@Override
	public ConsultationMonthInfo getMonthInformation(Long agentId, String date) {

		YearMonth yearMonth = YearMonth.parse(date);
		LocalDateTime startOfMonth = yearMonth.atDay(1).atTime(LocalTime.MIN);
		LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
		int days = yearMonth.lengthOfMonth();

		// status information
		List<Consultation> consultations = consultationRepository.findAllByCustomer_AgentIdAndDateBetween(agentId,
			startOfMonth, endOfMonth);

		Map<ConsultationStatus, Long> statusCountMap = consultations.stream()
			.collect(Collectors.groupingBy(Consultation::getConsultationStatus, Collectors.counting()));

		// date count information
		List<Integer> dailyCount = new ArrayList<>(Collections.nCopies(days, 0));

		for (int i = 0; i < days; i++) {
			LocalDate localDate = yearMonth.atDay(i + 1);

			LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);
			LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
			dailyCount.set(i,
				consultationRepository.countByCustomer_AgentIdAndDateBetween(agentId, startOfDay, endOfDay));
		}

		return ConsultationMonthInfo.builder()
			.consultationAll((long)consultations.size())
			.consultationWaiting(statusCountMap.getOrDefault(Consultation.ConsultationStatus.WAITING, 0L))
			.consultationConfirmed(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CONFIRMED, 0L))
			.consultationCancelled(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CANCELED, 0L))
			.consultationCompleted(statusCountMap.getOrDefault(Consultation.ConsultationStatus.COMPLETED, 0L))
			.daysCount(dailyCount)
			.build();
	}

	@Override
	public ConsultationSummaryInfo getSummary(Long agentId) {
		LocalDate today = LocalDate.now();

		int total = consultationRepository.countTodayConsultations(agentId, today);
		int remain = consultationRepository.countTodayRemaining(agentId, today);

		return ConsultationSummaryInfo.builder().todayCount(total).remainingCount(remain).build();
	}

}
