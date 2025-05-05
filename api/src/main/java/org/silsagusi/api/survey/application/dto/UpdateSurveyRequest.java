package org.silsagusi.api.survey.application.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyRequest {
	@NotBlank
	private String title;

	@NotBlank
	private String description;

	private List<UpdateQuestion> questionList;

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateQuestion {
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
}
