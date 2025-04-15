package org.silsagusi.joonggaemoa.api.survey.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.api.survey.domain.QuestionAnswerPair;

import java.util.List;

@Getter
@Builder
public class QuestionAnswerResponse {
    private String question;
    private List<String> answer;

    public static QuestionAnswerResponse of(QuestionAnswerPair questionAnswerPair) {
        return QuestionAnswerResponse.builder()
            .question(questionAnswerPair.getQuestion())
            .answer(questionAnswerPair.getAnswer())
            .build();
    }
}
