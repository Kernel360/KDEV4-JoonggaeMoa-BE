package org.silsagusi.joonggaemoa.domain.survey.service.command;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.survey.entity.Answer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class AnswerCommand {
    private Boolean applyConsultation;
    private LocalDateTime consultAt;
    private Customer customer;
    private SurveyCommand survey;
    private List<QuestionAnswerCommand> answers;
    private String createdAt;

    public static AnswerCommand of(Answer answer) {
        List<QuestionAnswerCommand> questionAnswerCommandList = answer.getQuestionAnswerPairs().stream()
            .map(it -> QuestionAnswerCommand.of(it)).toList();

        return AnswerCommand.builder()
            .applyConsultation(answer.getApplyConsultation())
            .consultAt(answer.getConsultAt())
            .customer(answer.getCustomer())
            .survey(SurveyCommand.of(answer.getSurvey()))
            .answers(questionAnswerCommandList)
            .createdAt(answer.getCreatedAt())
            .build();
    }
}
