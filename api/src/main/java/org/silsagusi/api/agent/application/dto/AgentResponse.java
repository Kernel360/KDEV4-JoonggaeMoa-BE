package org.silsagusi.api.agent.application.dto;

import org.silsagusi.core.domain.agent.Agent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgentResponse {
	private Long id;
	private String name;
	private String phone;
	private String email;
	private String username;
	private String office;
	private String region;
	private String businessNo;
	private String role;

	public static AgentResponse toResponse(Agent agent) {
		return AgentResponse.builder()
			.id(agent.getId())
			.name(agent.getName())
			.phone(agent.getPhone())
			.email(agent.getEmail())
			.username(agent.getUsername())
			.office(agent.getOffice())
			.region(agent.getRegion())
			.businessNo(agent.getBusinessNo())
			.build();
	}
}
