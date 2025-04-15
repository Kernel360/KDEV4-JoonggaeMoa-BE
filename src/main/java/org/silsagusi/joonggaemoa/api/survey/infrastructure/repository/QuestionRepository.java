<<<<<<<< HEAD:src/main/java/org/silsagusi/joonggaemoa/api/survey/infrastructure/repository/QuestionRepository.java
package org.silsagusi.joonggaemoa.api.survey.infrastructure.repository;

import org.silsagusi.joonggaemoa.api.survey.domain.entity.Question;
========
package org.silsagusi.joonggaemoa.api.survey.infrastructure;

import org.silsagusi.joonggaemoa.api.survey.domain.Question;
>>>>>>>> develop:src/main/java/org/silsagusi/joonggaemoa/api/survey/infrastructure/QuestionRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
