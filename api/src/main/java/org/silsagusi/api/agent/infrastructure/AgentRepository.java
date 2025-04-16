package org.silsagusi.api.agent.infrastructure;

import org.silsagusi.core.domain.agent.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	Optional<Agent> findByUsername(String username);

	Optional<Agent> findByNameAndPhone(String name, String phone);

	boolean existsByUsername(String username);

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);
}
