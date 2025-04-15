package org.silsagusi.joonggaemoa.domain.survey.infrastructure;

import org.silsagusi.joonggaemoa.domain.survey.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
