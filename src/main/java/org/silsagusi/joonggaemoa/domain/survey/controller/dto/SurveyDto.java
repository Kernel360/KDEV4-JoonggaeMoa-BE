package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.SurveyCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SurveyDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String title;
		private String description;
		private List<QuestionDto.Response> questionList;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String title;
		private String description;
		private List<QuestionDto.Response> questionList;

		public static Response of(SurveyCommand surveyCommand) {
			List<QuestionDto.Response> questionResponseList = surveyCommand.getQuestionList()
				.stream().map(QuestionDto.Response::of).toList();

			return Response.builder()
				.id(surveyCommand.getId())
				.title(surveyCommand.getTitle())
				.description(surveyCommand.getDescription())
				.questionList(questionResponseList)
				.build();
		}
	}
}