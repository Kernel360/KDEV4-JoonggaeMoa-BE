package org.silsagusi.joonggaemoa.api.consultation.infrastructure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.api.consultation.domain.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

	List<Consultation> findAllByCustomer_AgentIdAndDateBetween(Long agentId, LocalDateTime startdate,
		LocalDateTime endDate);

	Integer countByCustomer_AgentIdAndDateBetween(Long agentId, LocalDateTime startOfDay, LocalDateTime endOfDay);

	Page<Consultation> findByDateBetweenAndConsultationStatus(
		LocalDateTime start, LocalDateTime end,
		Consultation.ConsultationStatus status,
		Pageable pageable
	);

	// 오늘 상담 전체 건수
	@Query("SELECT COUNT(c) FROM consultations c " +
		"WHERE DATE(c.date) = :today " +
		"AND c.customer.agent.id = :agentId " +
		"AND c.consultationStatus IN ('WAITING', 'CONFIRMED', 'COMPLETED')")
	int countTodayConsultations(@Param("agentId") Long agentId, @Param("today") LocalDate today);

	// 오늘 남은 상담 (완료 안 된 것)
	@Query("SELECT COUNT(c) FROM consultations c " +
		"WHERE DATE(c.date) = :today " +
		"AND c.customer.agent.id = :agentId " +
		"AND c.consultationStatus IN ('WAITING', 'CONFIRMED')")
	int countTodayRemaining(@Param("agentId") Long agentId, @Param("today") LocalDate today);

}
