package org.silsagusi.joonggaemoa.domain.message.entity;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "message_templates")
@Getter
public class MessageTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_template_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Agent agent;

	@Enumerated(EnumType.STRING)
	private Category category;

	@Setter
	private String content;

	public MessageTemplate(Agent agent, Category category, String content) {
		this.agent = agent;
		this.category = category;
		this.content = content;
	}

	public static MessageTemplate create(Agent agent, Category category, String content) {
		return new MessageTemplate(agent, category, content);
	}
}
