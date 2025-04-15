package org.silsagusi.joonggaemoa.domain.message.infrastructure;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.domain.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
	List<MessageTemplate> findByAgentId(Long agentId);
}