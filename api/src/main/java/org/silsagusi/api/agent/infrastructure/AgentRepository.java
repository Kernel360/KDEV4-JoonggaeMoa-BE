package org.silsagusi.api.agent.infrastructure;

import java.util.Optional;

import org.silsagusi.core.domain.agent.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	Optional<Agent> findByUsernameAndDeletedAtIsNull(String username);

	Optional<Agent> findByNameAndPhoneAndDeletedAtIsNull(String name, String phone);

	boolean existsByUsernameAndDeletedAtIsNull(String username);

	boolean existsByPhoneAndDeletedAtIsNull(String phone);

	boolean existsByEmailAndDeletedAtIsNull(String email);

	Optional<Agent> findByIdAndDeletedAtIsNull(Long agentId);
}
