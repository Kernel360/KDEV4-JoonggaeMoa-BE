package org.silsagusi.joonggaemoa.domain.survey.service.command;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.entity.Survey;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyCommand {
	private String id;
	private Long agentId;
	private String title;
	private String description;
	private List<QuestionCommand> questionList;
	private String createdAt;

	public static SurveyCommand of(Survey survey) {
		List<QuestionCommand> questionCommandList = survey.getQuestionList()
			.stream().map(it -> QuestionCommand.of(it)).toList();

		return SurveyCommand.builder()
			.id(survey.getId())
			.agentId(survey.getAgent().getId())
			.title(survey.getTitle())
			.description(survey.getDescription())
			.questionList(questionCommandList)
			.createdAt(survey.getCreatedAt())
			.build();
	}

}
