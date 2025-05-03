package org.silsagusi.api.agent.application.mapper;

import org.silsagusi.api.agent.application.dto.AgentSignUpRequest;
import org.silsagusi.core.domain.agent.Agent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentMapper {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Agent toEntity(AgentSignUpRequest agentSignUpRequest) {
		return org.silsagusi.core.domain.agent.Agent.create(
			agentSignUpRequest.getName(),
			agentSignUpRequest.getPhone(),
			agentSignUpRequest.getEmail(),
			agentSignUpRequest.getUsername(),
			bCryptPasswordEncoder.encode(agentSignUpRequest.getPassword()),
			agentSignUpRequest.getOffice(),
			agentSignUpRequest.getRegion(),
			agentSignUpRequest.getBusinessNo()
		);
	}
}
