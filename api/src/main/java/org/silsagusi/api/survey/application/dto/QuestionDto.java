package org.silsagusi.api.survey.application.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
