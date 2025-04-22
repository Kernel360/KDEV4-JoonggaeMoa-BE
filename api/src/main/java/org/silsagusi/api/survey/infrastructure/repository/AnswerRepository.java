package org.silsagusi.api.survey.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	Page<Answer> findAllByCustomer_AgentIdAndDeletedAtIsNull(Long agentId, Pageable pageable);

	List<Answer> findAllByCustomerAndCreatedAtBetweenAndDeletedAtIsNull(Customer customer, LocalDateTime startDate,
		LocalDateTime endDate);
}
