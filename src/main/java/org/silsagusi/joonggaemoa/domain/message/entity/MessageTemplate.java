package org.silsagusi.joonggaemoa.domain.message.entity;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

	private String category;

	private String content;

}
