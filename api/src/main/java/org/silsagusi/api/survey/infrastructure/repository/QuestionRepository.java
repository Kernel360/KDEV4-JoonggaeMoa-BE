package org.silsagusi.api.survey.infrastructure.repository;

import org.silsagusi.core.domain.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
