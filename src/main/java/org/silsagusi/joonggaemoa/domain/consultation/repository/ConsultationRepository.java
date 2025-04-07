package org.silsagusi.joonggaemoa.domain.consultation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

	List<Consultation> findAllByCustomer_AgentIdAndDateBetween(Long agentId, LocalDateTime startdate,
		LocalDateTime endDate);

	Integer countByCustomer_AgentIdAndDateBetween(Long agentId, LocalDateTime startOfDay, LocalDateTime endOfDay);

	Page<Consultation> findByDateBetweenAndConsultationStatus(
		LocalDateTime start, LocalDateTime end,
		Consultation.ConsultationStatus status,
		Pageable pageable
	);
}
