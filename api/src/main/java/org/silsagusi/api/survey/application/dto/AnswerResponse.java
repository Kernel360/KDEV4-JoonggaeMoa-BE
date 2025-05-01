package org.silsagusi.api.survey.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.customer.application.dto.CustomerResponse;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.silsagusi.core.domain.survey.entity.QuestionAnswerPair;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponse {

	private CustomerResponse customer;
	private SurveyResponse survey;
	private LocalDateTime createdAt;
	private List<QuestionAnswerResponse> answer;

	@Getter
	@Builder
	public static class QuestionAnswerResponse {
		private String question;
		private List<String> answer;

		public static QuestionAnswerResponse toResponse(QuestionAnswerPair questionAnswerPair) {
			return QuestionAnswerResponse.builder()
				.question(questionAnswerPair.getQuestion())
				.answer(questionAnswerPair.getAnswer())
				.build();
		}
	}

	public static AnswerResponse toResponse(Answer answer) {
		List<QuestionAnswerResponse> questionAnswerResponseList = answer.getQuestionAnswerPairs().stream()
			.map(QuestionAnswerResponse::toResponse).toList();

		return AnswerResponse.builder()
			.customer(CustomerResponse.toResponse(answer.getCustomer()))
			.survey(SurveyResponse.toResponse(answer.getSurvey()))
			.answer(questionAnswerResponseList)
			.createdAt(answer.getCreatedAt())
			.build();
	}
}
