package org.silsagusi.joonggaemoa.domain.message.repository;

import java.util.Optional;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.message.entity.Category;
import org.silsagusi.joonggaemoa.domain.message.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
	Optional<MessageTemplate> findByAgentAndCategory(Agent agent, Category category);
}