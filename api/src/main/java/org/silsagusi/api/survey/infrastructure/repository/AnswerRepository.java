package org.silsagusi.api.survey.infrastructure.repository;

import org.silsagusi.core.domain.survey.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	Page<Answer> findAllByCustomer_AgentId(Long agentId, Pageable pageable);
}
