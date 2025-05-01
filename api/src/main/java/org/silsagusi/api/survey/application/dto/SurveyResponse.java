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
public class SurveyResponse {
	private String id;
	private String title;
	private String description;
	private List<QuestionResponse> questionList;
	private LocalDateTime createdAt;

	@Getter
	@Builder
	public static class QuestionResponse {
		private Long id;
		private String surveyId;
		private String content;
		private String type;
		private Boolean isRequired;
		private List<String> options;
	}

	public static SurveyResponse toResponse(Survey survey) {
		return SurveyResponse.builder()
			.id(survey.getId())
			.title(survey.getTitle())
			.description(survey.getDescription())
			.questionList(toQuestionResponse(survey.getQuestionList()))
			.createdAt(survey.getCreatedAt())
			.build();
	}

	private static List<QuestionResponse> toQuestionResponse(List<Question> questions) {
		return questions.stream()
			.map(question -> QuestionResponse.builder()
				.id(question.getId())
				.surveyId(question.getSurvey().getId())
				.content(question.getContent())
				.type(question.getType())
				.isRequired(question.getIsRequired())
				.options(new ArrayList<>(question.getOptions()))
				.build())
			.toList();
	}
}
