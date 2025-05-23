package org.silsagusi.core.domain.survey.entity;

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

	private QuestionAnswerPair(
		String question,
		List<String> answer
	) {
		this.question = question;
		this.answer = answer;
	}

	public static QuestionAnswerPair create(
		String question,
		List<String> answer
	) {
		return new QuestionAnswerPair(question, answer);
	}
}