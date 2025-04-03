package org.silsagusi.joonggaemoa.domain.survey.entity;

import java.util.List;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class QuestionAnswerPair {
	private String question;
	private List<String> answer;

	public QuestionAnswerPair(
		String question,
		List<String> answer
	) {
		this.question = question;
		this.answer = answer;
	}
}