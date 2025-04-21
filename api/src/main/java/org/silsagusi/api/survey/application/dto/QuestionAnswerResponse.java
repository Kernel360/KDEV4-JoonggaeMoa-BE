package org.silsagusi.api.survey.application.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionAnswerResponse {
	private String question;
	private List<String> answer;
}
