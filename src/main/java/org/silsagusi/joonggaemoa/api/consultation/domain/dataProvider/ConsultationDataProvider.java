package org.silsagusi.joonggaemoa.api.consultation.domain.dataProvider;

import org.silsagusi.joonggaemoa.api.consultation.domain.entity.Consultation;
import org.silsagusi.joonggaemoa.api.consultation.domain.info.ConsultationMonthInfo;
import org.silsagusi.joonggaemoa.api.consultation.domain.info.ConsultationSummaryInfo;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationDataProvider {

    //TODO
    Customer getCustomer(Long customerId);

    void createConsultation(Customer customer, LocalDateTime consultationDate);

    Consultation getConsultation(Long consultationId);

    void validateAgentAccess(Long agentId, Consultation consultation);

    void updateStatus(Consultation consultation, String consultationStatus);

    void updateConcsultation(
        Consultation consultation, LocalDateTime date, String purpose, String interestProperty, String interestLocation,
        String contractType, String assetStatus, String memo, String consultationStatus
    );

    List<Consultation> getConsultationByDate(Long agentId, LocalDateTime date);

    ConsultationMonthInfo getMonthInformation(Long agentId, String date);

    ConsultationSummaryInfo getSummary(Long agentId);

}
