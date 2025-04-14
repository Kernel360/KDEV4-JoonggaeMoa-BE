package org.silsagusi.joonggaemoa.domain.survey.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.domain.customer.service.dto.CustomerDto;
import org.silsagusi.joonggaemoa.domain.survey.service.command.AnswerCommand;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class AnswerDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
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
