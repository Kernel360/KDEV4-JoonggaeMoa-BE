package org.silsagusi.api.agent.application;

import org.silsagusi.api.agent.application.dto.AgentDto;
import org.silsagusi.api.agent.application.dto.UpdateAgentRequest;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.agent.command.UpdateAgentCommand;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
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
}
