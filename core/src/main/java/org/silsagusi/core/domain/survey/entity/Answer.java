package org.silsagusi.core.domain.survey.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.customer.entity.Customer;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "answers")
@Getter
public class Answer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;

	@Column(name = "apply_consultation")
	private Boolean applyConsultation;

	@Column(name = "consult_at")
	private LocalDateTime consultAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id")
	private Survey survey;

	@ElementCollection
	@CollectionTable(name = "question_answer_pairs", joinColumns = @JoinColumn(name = "question_answer_pair_id"))
	private List<QuestionAnswerPair> questionAnswerPairs;

	private Answer(
		Boolean applyConsultation,
		LocalDateTime consultAt,
		Customer customer,
		Survey survey,
		List<QuestionAnswerPair> questionAnswerPairs
	) {
		this.applyConsultation = applyConsultation;
		this.consultAt = consultAt;
		this.customer = customer;
		this.survey = survey;
		this.questionAnswerPairs = questionAnswerPairs;
	}

	public static Answer create(
		Boolean applyConsultation,
		LocalDateTime consultAt,
		Customer customer,
		Survey survey,
		List<QuestionAnswerPair> questionAnswerPairs
	) {
		return new Answer(applyConsultation, consultAt, customer, survey, questionAnswerPairs);
	}
}
