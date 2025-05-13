package org.silsagusi.api.consultation.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.consultation.enums.ConsultationStatus;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>, ConsultationCustomRepository {

	List<Consultation> findAllByAgent_IdAndDateBetweenAndDeletedAtIsNull(Long agentId, LocalDateTime startDate,
		LocalDateTime endDate);

	Integer countByAgent_IdAndDateBetweenAndDeletedAtIsNull(Long agentId, LocalDateTime startOfDay,
		LocalDateTime endOfDay);

	List<Consultation> findByDateBetweenAndConsultationStatusAndDeletedAtIsNull(LocalDateTime start, LocalDateTime end,
		ConsultationStatus status);

	Optional<Consultation> findByIdAndDeletedAtIsNull(Long consultationId);

	Page<Consultation> findByCustomerAndDeletedAtIsNull(Customer customer, Pageable pageable);

	List<Consultation> findByCustomerAndDateBetweenAndDeletedAtIsNull(Customer customer, LocalDateTime start,
		LocalDateTime end);

	List<Consultation> findAllByAgent_IdAndDateBetweenAndConsultationStatusAndDeletedAtIsNull(
		Long customerAgentId, LocalDateTime start, LocalDateTime end, ConsultationStatus consultationStatus);
}
