package org.silsagusi.joonggaemoa.domain.survey.entity;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;

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

@NoArgsConstructor
@ToString
@Entity(name = "answer")
@Getter
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id")
	private Survey survey;

	@ElementCollection
	@CollectionTable(name = "question_answers", joinColumns = @JoinColumn(name = "answer_id"))
	private List<QuestionAnswer> answer;

	public Answer(
		Customer customer,
		Survey survey,
		List<QuestionAnswer> answer
	) {
		this.customer = customer;
		this.survey = survey;
		this.answer = answer;
	}
}
