package org.silsagusi.joonggaemoa.domain.agent.infrastructure;

import org.silsagusi.joonggaemoa.domain.agent.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	Optional<Agent> findByUsername(String username);

	Optional<Agent> findByNameAndPhone(String name, String phone);

	boolean existsByUsername(String username);

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);
}
