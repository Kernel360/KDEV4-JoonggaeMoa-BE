package org.silsagusi.joonggaemoa.api.survey.infrastructure.repository;

import org.silsagusi.joonggaemoa.api.survey.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
