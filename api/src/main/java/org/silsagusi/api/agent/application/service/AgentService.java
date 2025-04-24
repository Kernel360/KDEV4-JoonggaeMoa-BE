package org.silsagusi.api.agent.application.service;

import org.silsagusi.api.agent.application.dto.AgentDto;
import org.silsagusi.api.agent.application.dto.UpdateAgentRequest;
import org.silsagusi.api.agent.application.dto.UsernameDto;
import org.silsagusi.api.agent.application.mapper.AgentMapper;
import org.silsagusi.api.agent.application.validator.AgentValidator;
import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.message.infrastructure.dataProvider.MessageTemplateDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.agent.command.UpdateAgentCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {

	private final AgentDataProvider agentDataProvider;
	private final AgentMapper agentMapper;
	private final AgentValidator agentValidator;
	private final MessageTemplateDataProvider messageTemplateDataProvider;

	@Transactional
	public void signup(AgentDto.Request agentRequest) {
		Agent agent = agentMapper.toEntity(agentRequest);

		agentValidator.validateExist(agent);

		agentDataProvider.createAgent(agent);

		messageTemplateDataProvider.createDefaultMessageTemplate(agent);
	}

	@Transactional
	public UsernameDto.Response getAgentByNameAndPhone(UsernameDto.Request usernameRequest) {
		Agent agent = agentDataProvider.getAgentByNameAndPhone(usernameRequest.getName(),
			usernameRequest.getPhone());

		return agentMapper.toUsernameResponse(agent);
	}

	@Transactional
	public void logout(String accessToken) {
		agentDataProvider.deleteRefreshTokenByAccessToken(accessToken);
	}

	@Transactional(readOnly = true)
	public AgentDto.Response getAgent(Long agentId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		return agentMapper.toAgentResponse(agent);
	}

	private final EntityManager em;

	@Transactional
	public void updateAgent(Long agentId, UpdateAgentRequest updateAgentRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		UpdateAgentCommand updateAgentCommand = agentMapper.toCommand(updateAgentRequest);

		System.out.println("트랜잭션 활성화 여부 : " + TransactionSynchronizationManager.isActualTransactionActive());

		agentDataProvider.updateAgent(agent, updateAgentCommand);

		System.out.println("Before flush");
		em.flush();
		System.out.println("After flush");
	}
}