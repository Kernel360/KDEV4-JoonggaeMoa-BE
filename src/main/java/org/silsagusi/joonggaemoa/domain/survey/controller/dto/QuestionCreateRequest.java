package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionCreateRequest {
	private String content;
	private String type;
	private Boolean isRequired;
	private List<String> options;
}
