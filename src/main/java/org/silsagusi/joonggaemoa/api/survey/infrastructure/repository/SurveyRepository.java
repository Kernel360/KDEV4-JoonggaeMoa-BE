package org.silsagusi.joonggaemoa.api.survey.infrastructure.repository;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.core.domain.survey.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, String> {
	Page<Survey> findAll(Pageable pageable);

	Page<Survey> findAllByAgent(Agent agent, Pageable pageable);
}
