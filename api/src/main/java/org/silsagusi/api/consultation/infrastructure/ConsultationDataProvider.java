package org.silsagusi.api.consultation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.entity.Consultation.ConsultationStatus;
import org.silsagusi.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.core.domain.consultation.info.ConsultationSummaryInfo;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConsultationDataProvider {

    private final ConsultationRepository consultationRepository;

    public void createConsultation(Customer customer, LocalDateTime consultationDate,
                                   ConsultationStatus consultationStatus) {

        Consultation consultation = new Consultation(
            customer,
            consultationDate,
            consultationStatus
        );

        consultationRepository.save(consultation);
    }

    public Consultation getConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        return consultation;
    }

    public void validateAgentAccess(Long agentId, Consultation consultation) {
        if (!consultation.getCustomer().getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    public void updateStatus(Consultation consultation, String consultationStatus) {
        consultation.updateStatus(Consultation.ConsultationStatus.valueOf(consultationStatus));
        consultationRepository.save(consultation);
    }

    public void updateConsultation(Consultation consultation, UpdateConsultationCommand updateConsultationCommand) {
        consultation.updateConsultation(updateConsultationCommand.getDate(), updateConsultationCommand.getPurpose(), updateConsultationCommand.getInterestProperty(), updateConsultationCommand.getInterestLocation(),
            updateConsultationCommand.getContractType(), updateConsultationCommand.getAssetStatus(), updateConsultationCommand.getMemo(), ConsultationStatus.valueOf(updateConsultationCommand.getConsultationStatus())
        );
        consultationRepository.save(consultation);
    }

    public List<Consultation> getConsultationByDate(Long agentId, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(LocalTime.MAX);

        List<Consultation> consultationList = consultationRepository.findAllByCustomer_AgentIdAndDateBetween(agentId,
            startOfDay, endOfDay);
        return consultationList;
    }

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
            .consultationAll((long) consultations.size())
            .consultationWaiting(statusCountMap.getOrDefault(Consultation.ConsultationStatus.WAITING, 0L))
            .consultationConfirmed(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CONFIRMED, 0L))
            .consultationCancelled(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CANCELED, 0L))
            .consultationCompleted(statusCountMap.getOrDefault(Consultation.ConsultationStatus.COMPLETED, 0L))
            .daysCount(dailyCount)
            .build();
    }

    public ConsultationSummaryInfo getSummary(Long agentId) {
        LocalDate today = LocalDate.now();

        int total = consultationRepository.countTodayConsultations(agentId, today);
        int remain = consultationRepository.countTodayRemaining(agentId, today);

        return ConsultationSummaryInfo.builder().todayCount(total).remainingCount(remain).build();
    }

}
