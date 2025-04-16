package org.silsagusi.api.survey.infrastructure.repository;

import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.survey.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, String> {
	Page<Survey> findAll(Pageable pageable);

	Page<Survey> findAllByAgent(Agent agent, Pageable pageable);
}
