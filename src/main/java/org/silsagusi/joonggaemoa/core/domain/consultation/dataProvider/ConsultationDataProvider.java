package org.silsagusi.joonggaemoa.core.domain.consultation.dataProvider;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.core.domain.consultation.entity.Consultation;
import org.silsagusi.joonggaemoa.core.domain.consultation.info.ConsultationMonthInfo;
import org.silsagusi.joonggaemoa.core.domain.consultation.info.ConsultationSummaryInfo;
import org.silsagusi.joonggaemoa.core.domain.customer.entity.Customer;

public interface ConsultationDataProvider {

	void createConsultation(Customer customer, LocalDateTime consultationDate,
		Consultation.ConsultationStatus consultationStatus);

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
