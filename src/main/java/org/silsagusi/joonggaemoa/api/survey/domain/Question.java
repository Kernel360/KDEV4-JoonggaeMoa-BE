package org.silsagusi.joonggaemoa.api.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public Question(Survey survey,
                    String content,
                    String type,
                    Boolean isRequired,
                    List<String> options
    ) {
        this.survey = survey;
        this.content = content;
        this.type = type;
        this.isRequired = isRequired;
        this.options = options;
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
