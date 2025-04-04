package org.silsagusi.joonggaemoa.domain.message.repository;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.message.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
	List<MessageTemplate> findByAgent(Agent agent);
}