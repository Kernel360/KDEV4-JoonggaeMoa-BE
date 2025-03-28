package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionAnswerCommand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionAnswerResponse {
	private String answer;
	private String question;

	public static QuestionAnswerResponse of(QuestionAnswerCommand command) {
		return QuestionAnswerResponse.builder()
			.answer(command.getAnswer())
			.question(command.getQuestion())
			.build();
	}
}
