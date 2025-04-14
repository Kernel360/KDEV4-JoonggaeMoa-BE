package org.silsagusi.joonggaemoa.domain.agent.service;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.agent.service.dto.AgentDto;
import org.silsagusi.joonggaemoa.domain.agent.service.dto.AgentUpdateRequest;
import org.silsagusi.joonggaemoa.domain.agent.service.dto.FindUsernameDto;
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

	@Transactional
	public void signup(AgentDto.Request requestDto) {

		if (agentRepository.existsByUsername(requestDto.getUsername())) {
			throw new CustomException(ErrorCode.CONFLICT_USERNAME);
		}

		if (agentRepository.existsByPhone(requestDto.getPhone())) {
			throw new CustomException(ErrorCode.CONFLICT_PHONE);
		}

		if (agentRepository.existsByEmail(requestDto.getEmail())) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}

		Agent agent = new Agent(
			requestDto.getName(),
			requestDto.getPhone(),
			requestDto.getEmail(),
			requestDto.getUsername(),
			bCryptPasswordEncoder.encode(requestDto.getPassword()),
			requestDto.getOffice(),
			requestDto.getRegion(),
			requestDto.getBusinessNo()
		);

		agentRepository.save(agent);

		messageTemplateService.createDefaultMessageTemplate(agent);
	}

	public FindUsernameDto.Response getAgentByNameAndPhone(FindUsernameDto.Request requestDto) {
		Agent agent = agentRepository.findByNameAndPhone(requestDto.getName(), requestDto.getPhone())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		return FindUsernameDto.Response.of(agent);
	}

	public void logout(String accessToken) {
		if (Boolean.FALSE.equals(jwtProvider.validateToken(accessToken))) {
			return;
		}
		Claims claims = jwtProvider.getClaims(accessToken);
		String username = claims.get("username", String.class);

		refreshTokenStore.deleteRefreshToken(username);
	}

	public AgentDto.Response getAgent(Long agentId) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		return AgentDto.Response.of(agent);
	}

	public void updateAgent(Long agentId, AgentUpdateRequest requestDto) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		agent.updateAgent(requestDto.getName(), requestDto.getPhone(), requestDto.getEmail(), requestDto.getUsername(),
			requestDto.getOffice(), requestDto.getRegion(), requestDto.getBusinessNo());

		agentRepository.save(agent);
	}
}