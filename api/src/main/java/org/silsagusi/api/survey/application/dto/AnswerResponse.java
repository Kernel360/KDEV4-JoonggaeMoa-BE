package org.silsagusi.api.survey.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.silsagusi.core.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.core.domain.survey.entity.Survey;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponse {

	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String customerEmail;
	private Boolean customerConsent;

	private String surveyTitle;
	private String surveyDescription;
	private LocalDateTime createdAt;

	private List<QuestionAnswerResponse> questionAnswers;

	@Getter
	@Builder
	public static class QuestionAnswerResponse {
		private String question;
		private List<String> answers;

		public static List<QuestionAnswerResponse> toResponses(List<QuestionAnswerPair> questionAnswerPairs) {
			return questionAnswerPairs.stream()
				.map(pair ->
					QuestionAnswerResponse.builder()
						.question(pair.getQuestion())
						.answers(new ArrayList<>(pair.getAnswer()))
						.build()
				).toList();
		}
	}

	public static AnswerResponse toResponse(Answer answer) {
		Customer customer = answer.getCustomer();
		Survey survey = answer.getSurvey();

		return AnswerResponse.builder()
			.customerId(customer.getId())
			.customerName(customer.getName())
			.customerPhone(customer.getPhone())
			.customerEmail(customer.getEmail())
			.customerConsent(customer.getConsent())
			.surveyTitle(survey.getTitle())
			.surveyDescription(survey.getDescription())
			.createdAt(answer.getCreatedAt())
			.questionAnswers(QuestionAnswerResponse.toResponses(answer.getQuestionAnswerPairs()))
			.build();
	}
}
