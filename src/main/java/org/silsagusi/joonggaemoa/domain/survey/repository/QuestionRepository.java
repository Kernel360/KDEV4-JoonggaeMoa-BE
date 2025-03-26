package org.silsagusi.joonggaemoa.domain.survey.repository;

import org.silsagusi.joonggaemoa.domain.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	void deleteBySurvey_Id(Long surveyId);
}
