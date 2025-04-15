package org.silsagusi.joonggaemoa.api.message.infrastructure;

import java.util.List;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.message.domain.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
	List<MessageTemplate> findByAgentId(Long agentId);

	List<MessageTemplate> findByAgent(Agent agent);
}