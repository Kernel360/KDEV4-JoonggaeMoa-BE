package org.silsagusi.joonggaemoa.domain.consultation.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;
import org.silsagusi.joonggaemoa.domain.consultation.repository.ConsultationRepository;
import org.silsagusi.joonggaemoa.domain.consultation.service.dto.ConsultationDto;
import org.silsagusi.joonggaemoa.domain.consultation.service.dto.ConsultationMonthInformResponse;
import org.silsagusi.joonggaemoa.domain.consultation.service.dto.ConsultationSummaryResponse;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final CustomerRepository customerRepository;

    public void createConsultation(ConsultationDto.Request consultationRequestDto) {
        Customer customer = customerRepository.findById(consultationRequestDto.getCustomerId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
        Consultation consultation = new Consultation(
            customer,
            consultationRequestDto.getDate(),
            Consultation.ConsultationStatus.CONFIRMED
        );
        consultationRepository.save(consultation);
    }

    public void updateConsultationStatus(Long agentId, Long consultationId, String consultationStatus) {
        Consultation consultation = consultationRepository.findById(consultationId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        if (!consultation.getCustomer().getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        consultation.updateStatus(Consultation.ConsultationStatus.valueOf(consultationStatus));
        consultationRepository.save(consultation);
    }

    public void updateConsultation(
        Long agentId,
        Long consultationId,
        LocalDateTime date,
        String purpose,
        String interestProperty,
        String interestLocation,
        String contractType,
        String assetStatus,
        String memo,
        String consultationStatus
    ) {
        Consultation consultation = consultationRepository.findById(consultationId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        if (!consultation.getCustomer().getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

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
                Consultation.ConsultationStatus.valueOf(consultationStatus)

        );
        consultationRepository.save(consultation);
    }

    public List<ConsultationDto.Response> getAllConsultationsByDate(Long agentId, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(LocalTime.MAX);

        List<Consultation> consultationList = consultationRepository.findAllByCustomer_AgentIdAndDateBetween(agentId,
            startOfDay, endOfDay);
        return consultationList.stream().map(ConsultationDto.Response::of).toList();
    }

    public ConsultationDto.Response getConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        return ConsultationDto.Response.of(consultation);
    }

    public ConsultationMonthInformResponse getMonthInformation(Long agentId, String date) {

        YearMonth yearMonth = YearMonth.parse(date);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atTime(LocalTime.MIN);
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        int days = yearMonth.lengthOfMonth();

        // status information
        List<Consultation> consultations = consultationRepository.findAllByCustomer_AgentIdAndDateBetween(agentId,
            startOfMonth, endOfMonth);

        Map<Consultation.ConsultationStatus, Long> statusCountMap = consultations.stream()
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

        return ConsultationMonthInformResponse.builder()
            .consultationAll((long) consultations.size())
            .consultationWaiting(statusCountMap.getOrDefault(Consultation.ConsultationStatus.WAITING, 0L))
            .consultationConfirmed(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CONFIRMED, 0L))
            .consultationCancelled(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CANCELED, 0L))
            .consultationCompleted(statusCountMap.getOrDefault(Consultation.ConsultationStatus.COMPLETED, 0L))
            .daysCount(dailyCount)
            .build();
    }

    public ConsultationSummaryResponse getConsultationSummary(Long agentId) {
        LocalDate today = LocalDate.now();

        int total = consultationRepository.countTodayConsultations(agentId, today);
        int remain = consultationRepository.countTodayRemaining(agentId, today);

        return new ConsultationSummaryResponse(total, remain);
    }
}
