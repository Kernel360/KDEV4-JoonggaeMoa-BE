package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionCommand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {
	private Long id;
	private Long surveyId;
	private String content;
	private String type;
	private Boolean isRequired;
	private List<String> options;

	public static QuestionResponse of(QuestionCommand questionCommand) {
		return QuestionResponse.builder()
			.id(questionCommand.getId())
			.surveyId(questionCommand.getSurveyId())
			.content(questionCommand.getContent())
			.type(questionCommand.getType())
			.isRequired(questionCommand.getIsRequired())
			.options(questionCommand.getOptions())
			.build();
	}
}
