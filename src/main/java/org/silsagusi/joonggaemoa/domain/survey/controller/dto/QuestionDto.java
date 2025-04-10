package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionCommand;

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

		public static Response of(QuestionCommand questionCommand) {
			return Response.builder()
				.id(questionCommand.getId())
				.surveyId(questionCommand.getSurveyId())
				.content(questionCommand.getContent())
				.type(questionCommand.getType())
				.isRequired(questionCommand.getIsRequired())
				.options(questionCommand.getOptions())
				.build();
		}
	}
}
