package org.silsagusi.joonggaemoa.domain.survey.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Entity(name = "questions")
@Getter
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long id;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
	private Survey survey;

	private String content;

	private String type;

	private Boolean isRequired;

	@ElementCollection
	@CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
	private List<String> options;

	public Question(Survey survey, String content, String type, Boolean isRequired, List<String> options) {
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
