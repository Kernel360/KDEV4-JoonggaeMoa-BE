package org.silsagusi.api.agent.application;

import org.silsagusi.api.agent.application.dto.AgentDto;
import org.silsagusi.api.agent.application.dto.UpdateAgentRequest;
import org.silsagusi.api.agent.application.dto.UsernameDto;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.agent.command.UpdateAgentCommand;
import org.springframework.stereotype.Component;

@Component
public class AgentMapper {

	public Agent toEntity(AgentDto.Request agentRequest) {
		return Agent.create(
			agentRequest.getName(),
			agentRequest.getPhone(),
			agentRequest.getEmail(),
			agentRequest.getUsername(),
			agentRequest.getPassword(),
			agentRequest.getOffice(),
			agentRequest.getRegion(),
			agentRequest.getBusinessNo()
		);
	}

	public UpdateAgentCommand toCommand(UpdateAgentRequest updateAgentRequest) {
		return UpdateAgentCommand.builder()
			.username(updateAgentRequest.getUsername())
			.name(updateAgentRequest.getName())
			.phone(updateAgentRequest.getPhone())
			.email(updateAgentRequest.getEmail())
			.office(updateAgentRequest.getOffice())
			.region(updateAgentRequest.getRegion())
			.businessNo(updateAgentRequest.getBusinessNo())
			.build();
	}

	public AgentDto.Response toAgentResponse(Agent agent) {
		return AgentDto.Response.builder()
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

	public UsernameDto.Response toUsernameResponse(Agent agent) {
		return new UsernameDto.Response(agent.getUsername());
	}
}
