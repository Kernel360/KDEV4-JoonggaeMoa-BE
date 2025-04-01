package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.controller.dto.CustomerDto;
import org.silsagusi.joonggaemoa.domain.survey.service.command.AnswerCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnswerDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String name;
		private String email;
		private String phone;
		private Boolean consent;
		private List<String> questions;
		private List<List<String>> answers;
	}

	@Getter
	@Builder
	public static class Response {
		private CustomerDto.Response customer;
		private SurveyDto.Response survey;
		private List<QuestionAnswerResponse> answer;
		private String createdAt;

		public static Response of(AnswerCommand command) {
			List<QuestionAnswerResponse> questionAnswerResponseList = command.getAnswers().stream()
				.map(QuestionAnswerResponse::of).toList();

			return Response.builder()
				.customer(CustomerDto.Response.of(command.getCustomer()))
				.survey(SurveyDto.Response.of(command.getSurvey()))
				.answer(questionAnswerResponseList)
				.createdAt(command.getCreatedAt())
				.build();
		}
	}
}
