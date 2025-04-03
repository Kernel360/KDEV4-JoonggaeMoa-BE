package org.silsagusi.joonggaemoa.domain.consultation.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;
import org.silsagusi.joonggaemoa.domain.consultation.repository.ConsultationRepository;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationCommand;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationStatusCommand;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final CustomerRepository customerRepository;

    public void createConsultation(
            Long customerId,
            LocalDateTime date
    ) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
        Consultation consultation = new Consultation(
                customer,
                date,
                Consultation.ConsultationStatus.CONFIRMED
        );
        consultationRepository.save(consultation);
    }

    public void updateConsultationStatus(Long consultationId, String consultationStatus) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        consultation.updateStatus(Consultation.ConsultationStatus.valueOf(consultationStatus));
        consultationRepository.save(consultation);
    }

    public void updateConsultation(
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


    public List<ConsultationCommand> getAllConsultationsByDate(LocalDateTime date) {
        List<Consultation> consultationList = consultationRepository.findAllByDate(date);
        return consultationList.stream().map(ConsultationCommand::of).toList();
    }

    public List<ConsultationCommand> getConsultationsByStatus(String consultationStatus) {
        List<Consultation> consultationList = consultationRepository.findAllByConsultationStatus(
                Consultation.ConsultationStatus.valueOf(consultationStatus));
        return consultationList.stream().map(ConsultationCommand::of).toList();
    }

    public ConsultationCommand getConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        return ConsultationCommand.of(consultation);
    }

    public ConsultationStatusCommand getStatusInformation() {
        List<Consultation> consultations = consultationRepository.findAll();

        Map<Consultation.ConsultationStatus, Long> statusCountMap = consultations.stream()
                .collect(Collectors.groupingBy(Consultation::getConsultationStatus, Collectors.counting()));

        return ConsultationStatusCommand.builder()
                .consultationAll((long) consultations.size())
                .consultationWaiting(statusCountMap.getOrDefault(Consultation.ConsultationStatus.WAITING, 0L))
                .consultationConfirmed(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CONFIRMED, 0L))
                .consultationCancelled(statusCountMap.getOrDefault(Consultation.ConsultationStatus.CANCELED, 0L))
                .consultationCompleted(statusCountMap.getOrDefault(Consultation.ConsultationStatus.COMPLETED, 0L))
                .build();
    }

}
