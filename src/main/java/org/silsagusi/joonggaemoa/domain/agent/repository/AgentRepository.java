package org.silsagusi.joonggaemoa.domain.agent.repository;

import java.util.Optional;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	Optional<Agent> findByUsername(String username);

	Optional<Agent> findByNameAndPhone(String name, String phone);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM agents a WHERE a.username = :username")
	boolean existsByUsername(@Param("username") String username);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM agents a WHERE a.phone = :phone")
	boolean existsByPhone(@Param("phone") String phone);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM agents a WHERE a.email = :email")
	boolean existsByEmail(@Param("email") String email);
}
