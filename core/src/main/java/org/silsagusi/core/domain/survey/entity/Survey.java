package org.silsagusi.core.domain.survey.entity;

import java.util.List;

import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.agent.Agent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "surveys")
@Getter
public class Survey extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "survey_id", columnDefinition = "CHAR(36)")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id")
	private Agent agent;

	private String title;

	private String description;

	@OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
	private List<Question> questionList = List.of();

	public Survey(Agent agent, String title, String description, List<Question> questionList) {
		this.agent = agent;
		this.title = title;
		this.description = description.isEmpty() ? "" : description;
		this.questionList = questionList;
	}

	public void updateSurveyTitleDescription(
		String title,
		String description
	) {
		this.title = title;
		this.description = description;
	}

}
