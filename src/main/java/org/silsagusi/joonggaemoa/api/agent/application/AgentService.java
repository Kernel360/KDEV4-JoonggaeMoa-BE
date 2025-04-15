package org.silsagusi.joonggaemoa.api.agent.application;

import org.silsagusi.joonggaemoa.api.agent.application.dto.AgentDto;
import org.silsagusi.joonggaemoa.api.agent.application.dto.AgentUpdateRequest;
import org.silsagusi.joonggaemoa.api.agent.application.dto.UsernameDto;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.domain.AgentDataProvider;
import org.silsagusi.joonggaemoa.api.message.domain.dataProvider.MessageTemplateDataProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AgentService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AgentDataProvider agentDataProvider;
	private final MessageTemplateDataProvider messageTemplateDataProvider;

	@Transactional
	public void signup(AgentDto.Request agentRequest) {
		Agent agent = new Agent(
			agentRequest.getName(),
			agentRequest.getPhone(),
			agentRequest.getEmail(),
			agentRequest.getUsername(),
			bCryptPasswordEncoder.encode(agentRequest.getPassword()),
			agentRequest.getOffice(),
			agentRequest.getRegion(),
			agentRequest.getBusinessNo()
		);

		agentDataProvider.validateExist(agent);

		agentDataProvider.createAgent(agent);

		messageTemplateDataProvider.createDefaultMessageTemplate(agent);
	}

	public UsernameDto.Response getAgentByNameAndPhone(UsernameDto.Request usernameRequest) {
		Agent agent = agentDataProvider.getAgentByNameAndPhone(usernameRequest.getName(),
			usernameRequest.getPhone());

		return UsernameDto.Response.of(agent);
	}

	public void logout(String accessToken) {
		agentDataProvider.deleteRefreshTokenByAccessToken(accessToken);
	}

	public AgentDto.Response getAgent(Long agentId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		return AgentDto.Response.of(agent);
	}

	public void updateAgent(Long agentId, AgentUpdateRequest agentUpdateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		agent.updateAgent(agentUpdateRequest.getName(), agentUpdateRequest.getPhone(), agentUpdateRequest.getEmail(),
			agentUpdateRequest.getUsername(),
			agentUpdateRequest.getOffice(), agentUpdateRequest.getRegion(), agentUpdateRequest.getBusinessNo());

		agentDataProvider.updateAgent(agent);
	}
}