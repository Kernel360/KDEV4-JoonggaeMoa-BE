package org.silsagusi.api.survey.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.customer.application.dto.CustomerResponse;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "AnswerRequestDto")
	public static class Request {

		@NotBlank
		private String name;

		@NotBlank
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		@NotBlank
		@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 000-0000-0000")
		private String phone;

		@NotNull
		private Boolean consent;

		@NotNull
		private Boolean applyConsultation;

		@Future
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		private LocalDateTime consultAt;

		private List<@NotBlank String> questions;

		private List<List<@NotBlank String>> answers;
	}

	@Getter
	@Builder
	public static class Response {
		private CustomerResponse customer;
		private SurveyDto.Response survey;
		private List<QuestionAnswerResponse> answer;
		private LocalDateTime createdAt;
	}

	public static Response toResponse(Answer answer) {
		List<QuestionAnswerResponse> questionAnswerResponseList = answer.getQuestionAnswerPairs().stream()
			.map(QuestionAnswerResponse::toResponse).toList();

		return Response.builder()
			.customer(CustomerResponse.toResponse(answer.getCustomer()))
			.survey(SurveyDto.toResponse(answer.getSurvey()))
			.answer(questionAnswerResponseList)
			.createdAt(answer.getCreatedAt())
			.build();
	}
}
