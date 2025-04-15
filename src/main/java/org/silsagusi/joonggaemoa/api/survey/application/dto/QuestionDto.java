package org.silsagusi.joonggaemoa.api.survey.application.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.api.survey.domain.entity.Question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuestionDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
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

		public static Response of(Question question) {
			return Response.builder()
				.id(question.getId())
				.surveyId(question.getSurvey().getId())
				.content(question.getContent())
				.type(question.getType())
				.isRequired(question.getIsRequired())
				.options(question.getOptions())
				.build();
		}
	}
}
