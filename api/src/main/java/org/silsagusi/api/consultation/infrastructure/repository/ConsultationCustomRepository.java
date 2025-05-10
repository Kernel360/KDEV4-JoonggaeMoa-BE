package org.silsagusi.api.consultation.infrastructure.repository;

import java.time.LocalDate;

public interface ConsultationCustomRepository {

	long countTodayConsultations(Long agentId, LocalDate today);

	long countTodayRemaining(Long agentId, LocalDate today);
}
