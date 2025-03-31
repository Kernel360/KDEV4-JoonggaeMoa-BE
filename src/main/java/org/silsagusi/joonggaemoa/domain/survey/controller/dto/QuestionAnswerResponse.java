package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionAnswerCommand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionAnswerResponse {
	private String question;
	private List<String> answer;

	public static QuestionAnswerResponse of(QuestionAnswerCommand command) {
		return QuestionAnswerResponse.builder()
			.question(command.getQuestion())
			.answer(command.getAnswer())
			.build();
	}
}
