package org.silsagusi.joonggaemoa.api.survey.infrastructure;

import org.silsagusi.joonggaemoa.api.survey.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
