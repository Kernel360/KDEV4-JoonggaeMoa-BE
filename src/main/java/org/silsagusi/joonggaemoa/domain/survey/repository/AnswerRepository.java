package org.silsagusi.joonggaemoa.domain.survey.repository;

import org.silsagusi.joonggaemoa.domain.survey.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAll(Pageable pageable);
}
