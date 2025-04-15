package org.silsagusi.joonggaemoa.api.customer.infrastructure;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByPhone(String phone);

	Page<Customer> findAllByAgent(Agent agent, Pageable pageable);

	Long countByAgentIdAndCreatedAtBetween(Long agentId, LocalDateTime start, LocalDateTime end);

	boolean existsByAgentAndPhone(Agent agent, String phone);

	boolean existsByAgentAndEmail(Agent agent, String email);
}
