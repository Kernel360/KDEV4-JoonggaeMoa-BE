package org.silsagusi.joonggaemoa.domain.agent.service;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.silsagusi.joonggaemoa.global.auth.jwt.JwtProvider;
import org.silsagusi.joonggaemoa.global.auth.jwt.RefreshTokenStore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {

	private final JwtProvider jwtProvider;
	private final AgentRepository agentRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final RefreshTokenStore refreshTokenStore;

	public void signup(
		String username,
		String password,
		String name,
		String phone,
		String email,
		String office,
		String region,
		String businessNo
	) {
		if (agentRepository.findByUsername(username).isPresent()) {
			throw new CustomException(ErrorCode.USERNAME_CONFLICT);
		}

		Agent agent = Agent.builder()
			.username(username)
			.password(bCryptPasswordEncoder.encode(password))
			.name(name)
			.phone(phone)
			.email(email)
			.office(office)
			.region(region)
			.businessNo(businessNo)
			.build();

		agentRepository.save(agent);
	}

	public AgentCommand getAgentByNameAndPhone(String name, String phone) {
		Agent agent = agentRepository.findByNameAndPhone(name, phone)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return AgentCommand.toCommand(agent);
	}

	public AgentCommand getAgentById(Long id) {
		Agent agent = agentRepository.getAgentById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return AgentCommand.toCommand(agent);
	}

	public void logout(String accessToken) {
		if (Boolean.FALSE.equals(jwtProvider.validateToken(accessToken))) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
		Claims claims = jwtProvider.getClaims(accessToken);
		String username = claims.get("username", String.class);

		refreshTokenStore.deleteRefreshToken(username);
	}
}