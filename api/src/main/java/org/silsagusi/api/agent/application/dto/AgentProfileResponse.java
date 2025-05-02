package org.silsagusi.api.agent.application.dto;

import org.silsagusi.core.domain.agent.Agent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgentProfileResponse {
	private String name;

	public static AgentProfileResponse toResponse(Agent agent) {
		return AgentProfileResponse.builder()
			.name(agent.getName())
			.build();
	}
}
