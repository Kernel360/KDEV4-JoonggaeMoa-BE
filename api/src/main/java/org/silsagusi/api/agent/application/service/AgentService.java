package org.silsagusi.api.agent.application.service;

import org.silsagusi.api.agent.application.dto.AgentResponse;
import org.silsagusi.api.agent.application.dto.AgentSignUpRequest;
import org.silsagusi.api.agent.application.dto.FindUsername;
import org.silsagusi.api.agent.application.dto.UpdateAgentRequest;
import org.silsagusi.api.agent.application.mapper.AgentMapper;
import org.silsagusi.api.agent.application.validator.AgentValidator;
import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.message.infrastructure.dataProvider.MessageTemplateDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.agent.command.UpdateAgentCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {

	private final AgentDataProvider agentDataProvider;
	private final AgentMapper agentMapper;
	private final AgentValidator agentValidator;
	private final MessageTemplateDataProvider messageTemplateDataProvider;

	@Transactional
	public void signup(AgentSignUpRequest agentSignUpRequest) {
		Agent agent = agentMapper.toEntity(agentSignUpRequest);
		agentValidator.validateExist(agent);

		agentDataProvider.createAgent(agent);
		messageTemplateDataProvider.createDefaultMessageTemplate(agent);
	}

	@Transactional
	public FindUsername.Response getAgentByNameAndPhone(FindUsername.Request usernameRequest) {
		Agent agent = agentDataProvider.getAgentByNameAndPhone(
			usernameRequest.getName(),
			usernameRequest.getPhone());

		return FindUsername.toResponse(agent);
	}

	@Transactional
	public void logout(String accessToken) {
		agentDataProvider.deleteRefreshTokenByAccessToken(accessToken);
	}

	@Transactional(readOnly = true)
	public AgentResponse getAgent(Long agentId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		return AgentResponse.toResponse(agent);
	}

	@Transactional
	public void updateAgent(Long agentId, UpdateAgentRequest updateAgentRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		UpdateAgentCommand updateAgentCommand = updateAgentRequest.toCommand();
		agentDataProvider.updateAgent(agent, updateAgentCommand);
	}
}