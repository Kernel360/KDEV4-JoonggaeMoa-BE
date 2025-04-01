package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuestionDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String content;
		private String type;
		private Boolean isRequired;
		private List<String> options;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private Long surveyId;
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
