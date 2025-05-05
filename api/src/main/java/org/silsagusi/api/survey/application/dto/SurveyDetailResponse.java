package org.silsagusi.api.survey.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.silsagusi.core.domain.survey.entity.Question;
import org.silsagusi.core.domain.survey.entity.Survey;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyDetailResponse {
	private String id;
	private String title;
	private String description;
	private LocalDateTime createdAt;
	private List<QuestionResponse> questions;

	@Getter
	@Builder
	public static class QuestionResponse {
		private Long id;
		private String content;
		private String type;
		private Boolean isRequired;
		private List<String> options;

		private static List<QuestionResponse> toResponses(List<Question> questions) {
			return questions.stream()
				.map(question -> QuestionResponse.builder()
					.id(question.getId())
					.content(question.getContent())
					.type(question.getType())
					.isRequired(question.getIsRequired())
					.options(new ArrayList<>(question.getOptions()))
					.build())
				.toList();
		}
	}

	public static SurveyDetailResponse toResponse(Survey survey) {
		return SurveyDetailResponse.builder()
			.id(survey.getId())
			.title(survey.getTitle())
			.description(survey.getDescription())
			.questions(QuestionResponse.toResponses(survey.getQuestionList()))
			.createdAt(survey.getCreatedAt())
			.build();
	}
}
