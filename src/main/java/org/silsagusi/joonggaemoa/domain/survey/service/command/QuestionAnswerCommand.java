package org.silsagusi.joonggaemoa.domain.survey.service.command;

import org.silsagusi.joonggaemoa.domain.survey.entity.QuestionAnswer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAnswerCommand {
	private String question;
	private String answer;

	public static QuestionAnswerCommand of(QuestionAnswer answer) {
		return QuestionAnswerCommand.builder()
			.question(answer.getQuestion())
			.answer(answer.getAnswer())
			.build();
	}
}
