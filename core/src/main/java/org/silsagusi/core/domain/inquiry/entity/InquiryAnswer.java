package org.silsagusi.core.domain.inquiry.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.agent.Agent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "inquiry_answers")
public class InquiryAnswer extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "inquiry_id")
	private Inquiry inquiry;

	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;

	private String content;

	private InquiryAnswer(Inquiry inquiry, Agent agent, String content) {
		this.inquiry = inquiry;
		this.agent = agent;
		this.content = content;
	}

	public static InquiryAnswer create(Inquiry inquiry, Agent agent, String content) {
		return new InquiryAnswer(inquiry, agent, content);
	}
}
