<<<<<<<< HEAD:src/main/java/org/silsagusi/joonggaemoa/api/survey/infrastructure/repository/AnswerRepository.java
package org.silsagusi.joonggaemoa.api.survey.infrastructure.repository;

import org.silsagusi.joonggaemoa.api.survey.domain.entity.Answer;
========
package org.silsagusi.joonggaemoa.api.survey.infrastructure;

import org.silsagusi.joonggaemoa.api.survey.domain.Answer;
>>>>>>>> develop:src/main/java/org/silsagusi/joonggaemoa/api/survey/infrastructure/AnswerRepository.java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAllByCustomer_AgentId(Long agentId, Pageable pageable);
}
