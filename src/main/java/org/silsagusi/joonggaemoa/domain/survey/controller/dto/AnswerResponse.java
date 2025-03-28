package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.controller.dto.CustomerResponse;
import org.silsagusi.joonggaemoa.domain.survey.service.command.AnswerCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponse {
	private CustomerResponse customer;
	private SurveyResponse survey;
	private List<QuestionAnswerResponse> answer;

	public static AnswerResponse of(AnswerCommand command) {
		List<QuestionAnswerResponse> questionAnswerResponseList = command.getAnswers().stream()
			.map(it -> QuestionAnswerResponse.of(it)).toList();
		return AnswerResponse.builder()
			.customer(CustomerResponse.of(command.getCustomer()))
			.survey(SurveyResponse.of(command.getSurvey()))
			.answer(questionAnswerResponseList)
			.build();
	}
}
