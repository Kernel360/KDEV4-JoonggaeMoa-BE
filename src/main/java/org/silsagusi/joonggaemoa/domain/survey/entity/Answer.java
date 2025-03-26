package org.silsagusi.joonggaemoa.domain.survey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.ToString;

@ToString
@Entity(name = "answer")
@Getter
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Question question;

}
