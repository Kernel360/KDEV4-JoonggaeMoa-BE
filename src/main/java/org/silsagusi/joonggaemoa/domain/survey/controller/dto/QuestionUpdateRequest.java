package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionUpdateRequest {
	private Long id;
	private String content;
	private String type;
	private Boolean isRequired;
	private List<String> options;

}
