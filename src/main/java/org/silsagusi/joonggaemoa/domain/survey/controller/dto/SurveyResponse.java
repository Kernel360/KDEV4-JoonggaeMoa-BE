package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.SurveyCommand;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SurveyResponse {
	private Long id;
	private String title;
	private String description;
	private List<QuestionResponse> questionList;

	public static SurveyResponse of(SurveyCommand surveyCommand) {
		List<QuestionResponse> questionResponseList = surveyCommand.getQuestionList()
			.stream().map(it -> QuestionResponse.of(it)).toList();

		return SurveyResponse.builder()
			.title(surveyCommand.getTitle())
			.description(surveyCommand.getDescription())
			.questionList(questionResponseList)
			.build();
	}
}
