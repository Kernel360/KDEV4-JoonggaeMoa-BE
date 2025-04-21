package org.silsagusi.api.message.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
	List<MessageTemplate> findByAgentIdAndDeletedAtIsNull(Long agentId);

	List<MessageTemplate> findByAgentAndDeletedAtIsNull(Agent agent);

	Optional<MessageTemplate> findByIdAndDeletedAtIsNull(Long templateId);
}