package org.silsagusi.core.domain.survey.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class QuestionAnswerPair {
    private String question;
    private List<String> answer;

    public QuestionAnswerPair(
        String question,
        List<String> answer
    ) {
        this.question = question;
        this.answer = answer;
    }
}