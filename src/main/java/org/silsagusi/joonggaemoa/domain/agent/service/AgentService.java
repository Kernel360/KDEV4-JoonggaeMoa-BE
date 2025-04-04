package org.silsagusi.joonggaemoa.domain.agent.service;

import org.silsagusi.joonggaemoa.domain.agent.controller.dto.AgentUpdateRequest;
import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;
import org.silsagusi.joonggaemoa.domain.message.service.MessageTemplateService;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.silsagusi.joonggaemoa.global.auth.jwt.JwtProvider;
import org.silsagusi.joonggaemoa.global.auth.jwt.RefreshTokenStore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AgentService {

	private final JwtProvider jwtProvider;
	private final AgentRepository agentRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final RefreshTokenStore refreshTokenStore;
	private final MessageTemplateService messageTemplateService;

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
		Agent agent = new Agent(
			name,
			phone,
			email,
			username,
			bCryptPasswordEncoder.encode(password),
			office,
			region,
			businessNo
		);

		agentRepository.save(agent);

		messageTemplateService.createDefaultMessageTemplate(agent);
	}

	public AgentCommand getAgentByNameAndPhone(String name, String phone) {
		Agent agent = agentRepository.findByNameAndPhone(name, phone)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		return AgentCommand.of(agent);
	}

	public void logout(String accessToken) {
		if (Boolean.FALSE.equals(jwtProvider.validateToken(accessToken))) {
			return;
		}
		Claims claims = jwtProvider.getClaims(accessToken);
		String username = claims.get("username", String.class);

		refreshTokenStore.deleteRefreshToken(username);
	}

	public AgentCommand getAgent(Long agentId) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		return AgentCommand.of(agent);
	}

	public void updateAgent(Long agentId, AgentUpdateRequest requestDto) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		System.out.println(requestDto);

		agent.updateAgent(requestDto.getName(), requestDto.getPhone(), requestDto.getEmail(), requestDto.getUsername(),
			requestDto.getOffice(), requestDto.getRegion(), requestDto.getBusinessNo());

		agentRepository.save(agent);
	}
}