package org.silsagusi.joonggaemoa.domain.survey.service.command;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.entity.QuestionAnswerPair;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAnswerCommand {
	private String question;
	private List<String> answer;

	public static QuestionAnswerCommand of(QuestionAnswerPair questionAnswerPair) {
		return QuestionAnswerCommand.builder()
			.question(questionAnswerPair.getQuestion())
			.answer(questionAnswerPair.getAnswer())
			.build();
	}
}
