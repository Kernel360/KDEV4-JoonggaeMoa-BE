package org.silsagusi.joonggaemoa.domain.survey.service.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.entity.QuestionAnswerPair;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionAnswerResponse {
	private String question;
	private List<String> answer;

	public static QuestionAnswerResponse of(QuestionAnswerPair questionAnswerPair) {
		return QuestionAnswerResponse.builder()
			.question(questionAnswerPair.getQuestion())
			.answer(questionAnswerPair.getAnswer())
			.build();
	}
}
