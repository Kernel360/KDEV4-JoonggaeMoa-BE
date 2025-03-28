package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class AnswerRequest {
	private String name;
	private String email;
	private String phone;
	private Boolean consent;
	private List<String> questions;
	private List<String> answers;
}
