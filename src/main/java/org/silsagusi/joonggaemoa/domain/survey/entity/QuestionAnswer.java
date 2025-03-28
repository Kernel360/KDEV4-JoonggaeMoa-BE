package org.silsagusi.joonggaemoa.domain.survey.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Getter
public class QuestionAnswer {
	private String question;
	private String answer;

	public QuestionAnswer(
		String answer,
		String question
	) {
		this.answer = answer;
		this.question = question;
	}
}