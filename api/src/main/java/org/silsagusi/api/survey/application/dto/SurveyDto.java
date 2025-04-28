package org.silsagusi.api.survey.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.survey.entity.Survey;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "SurveyCreateRequestDto")
	public static class CreateRequest {

		@NotBlank
		private String title;

		private String description;

		private List<QuestionDto.CreateRequest> questionList;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "SurveyUpdateRequestDto")
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
		private LocalDateTime createdAt;
	}

	public static Response toResponse(Survey survey) {
		List<QuestionDto.Response> questionResponseList = survey.getQuestionList()
			.stream().map(QuestionDto::toResponse).toList();

		return Response.builder()
			.id(survey.getId())
			.title(survey.getTitle())
			.description(survey.getDescription())
			.questionList(questionResponseList)
			.createdAt(survey.getCreatedAt())
			.build();
	}
}