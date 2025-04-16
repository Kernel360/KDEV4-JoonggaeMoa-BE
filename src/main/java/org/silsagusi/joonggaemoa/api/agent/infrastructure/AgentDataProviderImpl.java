package org.silsagusi.joonggaemoa.api.agent.infrastructure;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.core.domain.agent.AgentDataProvider;
import org.silsagusi.joonggaemoa.core.customResponse.exception.CustomException;
import org.silsagusi.joonggaemoa.core.customResponse.exception.ErrorCode;
import org.silsagusi.joonggaemoa.core.auth.jwt.JwtProvider;
import org.silsagusi.joonggaemoa.core.auth.jwt.RefreshTokenStore;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentDataProviderImpl implements AgentDataProvider {

	private final AgentRepository agentRepository;
	private final JwtProvider jwtProvider;
	private final RefreshTokenStore refreshTokenStore;

	@Override
	public void createAgent(Agent agent) {
		agentRepository.save(agent);
	}

	@Override
	public void validateExist(Agent agent) {
		if (agentRepository.existsByUsername(agent.getUsername())) {
			throw new CustomException(ErrorCode.CONFLICT_USERNAME);
		}

		if (agentRepository.existsByPhone(agent.getPhone())) {
			throw new CustomException(ErrorCode.CONFLICT_PHONE);
		}

		if (agentRepository.existsByEmail(agent.getEmail())) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}
	}

	@Override
	public Agent getAgentById(Long agentId) {
		return agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
	}

	@Override
	public Agent getAgentByNameAndPhone(String name, String phone) {
		return agentRepository.findByNameAndPhone(name, phone)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
	}

	@Override
	public void updateAgent(Agent agent) {
		agentRepository.save(agent);
	}

	@Override
	public void deleteRefreshTokenByAccessToken(String accessToken) {
		if (Boolean.FALSE.equals(jwtProvider.validateToken(accessToken))) {
			return;
		}
		Claims claims = jwtProvider.getClaims(accessToken);
		String username = claims.get("username", String.class);

		refreshTokenStore.deleteRefreshToken(username);
	}
}
