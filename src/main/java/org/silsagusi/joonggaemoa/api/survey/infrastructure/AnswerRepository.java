package org.silsagusi.joonggaemoa.api.survey.infrastructure;

import org.silsagusi.joonggaemoa.api.survey.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAllByCustomer_AgentId(Long agentId, Pageable pageable);
}
