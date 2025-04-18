package org.silsagusi.api.agent.infrastructure;

import org.silsagusi.api.agent.exception.AgentNotFoundException;
import org.silsagusi.api.auth.jwt.JwtProvider;
import org.silsagusi.api.auth.jwt.RefreshTokenStore;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.agent.command.UpdateAgentCommand;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentDataProvider {

	private final AgentRepository agentRepository;
	private final JwtProvider jwtProvider;
	private final RefreshTokenStore refreshTokenStore;

	public void createAgent(Agent agent) {
		agentRepository.save(agent);
	}

	public Agent getAgentById(Long agentId) {

		return agentRepository.findByIdAndDeletedAtIsNull(agentId)
			.orElseThrow(() -> new AgentNotFoundException(agentId));
	}

	public Agent getAgentByNameAndPhone(String name, String phone) {
		return agentRepository.findByNameAndPhoneAndDeletedAtIsNull(name, phone)
			.orElseThrow(() -> new AgentNotFoundException(name));
	}

	public void updateAgent(Agent agent, UpdateAgentCommand updateAgentCommand) {
		agent.updateAgent(updateAgentCommand.getName(), updateAgentCommand.getPhone(), updateAgentCommand.getEmail(),
			updateAgentCommand.getUsername(), updateAgentCommand.getOffice(), updateAgentCommand.getRegion(),
			updateAgentCommand.getBusinessNo());

		agentRepository.save(agent);
	}

	public void deleteRefreshTokenByAccessToken(String accessToken) {
		if (Boolean.FALSE.equals(jwtProvider.validateToken(accessToken))) {
			return;
		}
		Claims claims = jwtProvider.getClaims(accessToken);
		String username = claims.get("username", String.class);

		refreshTokenStore.deleteRefreshToken(username);
	}
}
