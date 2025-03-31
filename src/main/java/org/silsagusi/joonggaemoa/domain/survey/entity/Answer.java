package org.silsagusi.joonggaemoa.domain.survey.entity;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.global.BaseEntity;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "answers")
@Getter
public class Answer extends BaseEntity {

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
	@CollectionTable(name = "question_answer_pairs", joinColumns = @JoinColumn(name = "question_answer_pair_id"))
	private List<QuestionAnswerPair> questionAnswerPairs;

	public Answer(
		Customer customer,
		Survey survey,
		List<QuestionAnswerPair> questionAnswerPairs
	) {
		this.customer = customer;
		this.survey = survey;
		this.questionAnswerPairs = questionAnswerPairs;
	}
}
