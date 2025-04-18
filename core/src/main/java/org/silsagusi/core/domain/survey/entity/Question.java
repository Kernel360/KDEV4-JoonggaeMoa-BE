package org.silsagusi.core.domain.survey.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "questions")
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private String content;

    private String type;

    private Boolean isRequired;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> options;

    private Question(Survey survey, String content, String type, Boolean isRequired, List<String> options) {
        this.survey = survey;
        this.content = content;
        this.type = type;
        this.isRequired = isRequired;
        this.options = options;
    }

    public static Question create(Survey survey, String content, String type, Boolean isRequired,
                                  List<String> options) {
        return new Question(survey, content, type, isRequired, options);
    }

    public void updateQuestion(
        String content,
        String type,
        Boolean isRequired,
        List<String> options
    ) {
        this.content = content;
        this.type = type;
        this.isRequired = isRequired;
        this.options = options;
    }

}
