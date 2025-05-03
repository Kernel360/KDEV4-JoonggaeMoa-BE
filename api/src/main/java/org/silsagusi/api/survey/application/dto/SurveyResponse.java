package org.silsagusi.api.survey.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.survey.entity.Survey;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyResponse {
	private String id;
	private String title;
	private String description;
	private Integer count;
	private LocalDateTime createdAt;

	public static SurveyResponse toResponse(Survey survey) {
		return SurveyResponse.builder()
			.id(survey.getId())
			.title(survey.getTitle())
			.description(survey.getDescription())
			.count(survey.getQuestionList().size())
			.createdAt(survey.getCreatedAt())
			.build();
	}
}
