package org.silsagusi.api.agent.application.mapper;

import org.silsagusi.api.agent.application.dto.AgentDto;
import org.silsagusi.core.domain.agent.Agent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentMapper {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Agent toEntity(AgentDto.Request agentRequest) {
		return Agent.create(
			agentRequest.getName(),
			agentRequest.getPhone(),
			agentRequest.getEmail(),
			agentRequest.getUsername(),
			bCryptPasswordEncoder.encode(agentRequest.getPassword()),
			agentRequest.getOffice(),
			agentRequest.getRegion(),
			agentRequest.getBusinessNo()
		);
	}
}
