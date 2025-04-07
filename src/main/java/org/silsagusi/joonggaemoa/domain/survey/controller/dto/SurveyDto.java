package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.SurveyCommand;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SurveyDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateRequest {

		@NotBlank
		private String title;

		private String description;

		private List<QuestionDto.CreateRequest> questionList;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateRequest {

		@NotBlank
		private String title;

		@NotBlank
		private String description;

		private List<QuestionDto.UpdateRequest> questionList;
	}

	@Getter
	@Builder
	public static class Response {
		private String id;
		private String title;
		private String description;
		private List<QuestionDto.Response> questionList;
		private String createdAt;

		public static Response of(SurveyCommand surveyCommand) {
			List<QuestionDto.Response> questionResponseList = surveyCommand.getQuestionList()
				.stream().map(QuestionDto.Response::of).toList();

			return Response.builder()
				.id(surveyCommand.getId())
				.title(surveyCommand.getTitle())
				.description(surveyCommand.getDescription())
				.questionList(questionResponseList)
				.createdAt(surveyCommand.getCreatedAt())
				.build();
		}
	}
}