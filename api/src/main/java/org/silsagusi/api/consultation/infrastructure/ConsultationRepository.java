package org.silsagusi.api.consultation.infrastructure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

	List<Consultation> findAllByCustomer_AgentIdAndDateBetweenAndDeletedAtIsNull(Long agentId, LocalDateTime startDate,
		LocalDateTime endDate);

	Integer countByCustomer_AgentIdAndDateBetweenAndDeletedAtIsNull(Long agentId, LocalDateTime startOfDay,
		LocalDateTime endOfDay);

	List<Consultation> findByDateBetweenAndConsultationStatusAndDeletedAtIsNull(LocalDateTime start, LocalDateTime end,
		Consultation.ConsultationStatus status);

	// 오늘 상담 전체 건수
	@Query("SELECT COUNT(c) FROM consultations c " +
		"WHERE DATE(c.date) = :today " +
		"AND c.customer.agent.id = :agentId " +
		"AND c.consultationStatus IN ('WAITING', 'CONFIRMED', 'COMPLETED')" +
		"AND c.deletedAt IS NULL"
	)
	int countTodayConsultations(@Param("agentId") Long agentId, @Param("today") LocalDate today);

	// 오늘 남은 상담 (완료 안 된 것)
	@Query("SELECT COUNT(c) FROM consultations c " +
		"WHERE DATE(c.date) = :today " +
		"AND c.customer.agent.id = :agentId " +
		"AND c.consultationStatus IN ('WAITING', 'CONFIRMED')" +
		"AND c.deletedAt IS NULL"
	)
	int countTodayRemaining(@Param("agentId") Long agentId, @Param("today") LocalDate today);

	Optional<Consultation> findByIdAndDeletedAtIsNull(Long consultationId);

	Page<Consultation> findByCustomerAndDeletedAtIsNull(Customer customer, Pageable pageable);

	List<Consultation> findAllByCustomer_Agent_IdAndDateBetweenAndConsultationStatusAndDeletedAtIsNull(
		Long customerAgentId, LocalDateTime start, LocalDateTime end,
		Consultation.ConsultationStatus consultationStatus);
}
