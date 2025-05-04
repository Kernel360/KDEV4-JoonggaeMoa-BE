package org.silsagusi.api.customer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCustomRepository {

	Long countByAgentIdAndCreatedAtBetweenAndDeletedAtIsNull(Long agentId, LocalDateTime start, LocalDateTime end);

	boolean existsByAgentAndPhoneAndDeletedAtIsNull(Agent agent, String phone);

	boolean existsByAgentAndEmailAndDeletedAtIsNull(Agent agent, String email);

	Optional<Customer> findByIdAndDeletedAtIsNull(Long customerId);

	Page<Customer> findAllByAgent_IdAndDeletedAtIsNull(Long agentId, Pageable pageable);

	Optional<Customer> findByPhoneAndDeletedAtIsNull(String phone);

	Page<Customer> findAllByAgent_IdAndNameContainingIgnoreCaseAndDeletedAtIsNull(Long agentId, String name,
		Pageable pageable);
}
