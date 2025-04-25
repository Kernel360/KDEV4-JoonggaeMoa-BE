package org.silsagusi.api.survey.application.dto;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.core.domain.survey.entity.Question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "QuestionCreateRequestDto")
	public static class CreateRequest {

		@NotBlank
		private String content;

		@NotBlank
		private String type;

		@NotNull
		private Boolean isRequired;

		private List<String> options;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "QuestionUpdateRequestDto")
	public static class UpdateRequest {

		@NotBlank
		private Long id;

		@NotBlank
		private String content;

		@NotBlank
		private String type;

		@NotNull
		private Boolean isRequired;

		private List<String> options;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String surveyId;
		private String content;
		private String type;
		private Boolean isRequired;
		private List<String> options;
	}

	public static Response toResponse(Question question) {
		return Response.builder()
			.id(question.getId())
			.surveyId(question.getSurvey().getId())
			.content(question.getContent())
			.type(question.getType())
			.isRequired(question.getIsRequired())
			.options(new ArrayList<>(question.getOptions()))
			.build();
	}
}
