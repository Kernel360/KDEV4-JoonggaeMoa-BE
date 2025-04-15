package org.silsagusi.joonggaemoa.api.survey.application.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.api.survey.domain.entity.Survey;

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

		public static Response of(Survey survey) {
			List<QuestionDto.Response> questionResponseList = survey.getQuestionList()
				.stream().map(QuestionDto.Response::of).toList();

			return Response.builder()
				.id(survey.getId())
				.title(survey.getTitle())
				.description(survey.getDescription())
				.questionList(questionResponseList)
				.createdAt(survey.getCreatedAt())
				.build();
		}
	}
}