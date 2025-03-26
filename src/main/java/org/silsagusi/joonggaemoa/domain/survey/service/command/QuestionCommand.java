package org.silsagusi.joonggaemoa.domain.survey.service.command;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.entity.Question;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionCommand {
	private Long id;
	private Long surveyId;
	private String content;
	private String type;
	private Boolean isRequired;
	private List<String> options;

	public static QuestionCommand of(Question question) {
		return QuestionCommand.builder()
			.id(question.getId())
			.surveyId(question.getSurvey().getId())
			.content(question.getContent())
			.type(question.getType())
			.isRequired(question.getIsRequired())
			.options(question.getOptions())
			.build();
	}
}
