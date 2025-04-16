package org.silsagusi.api.message.infrastructure.repository;

import java.util.List;

import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
	List<MessageTemplate> findByAgentId(Long agentId);

	List<MessageTemplate> findByAgent(Agent agent);
}