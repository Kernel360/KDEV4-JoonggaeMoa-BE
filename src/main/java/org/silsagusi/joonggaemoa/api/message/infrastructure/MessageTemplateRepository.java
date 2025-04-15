package org.silsagusi.joonggaemoa.api.message.infrastructure;

import org.silsagusi.joonggaemoa.api.message.domain.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
    List<MessageTemplate> findByAgentId(Long agentId);
}