package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class SurveyUpdateRequest {
	private String title;
	private String description;
	private List<QuestionUpdateRequest> questionList;
}
