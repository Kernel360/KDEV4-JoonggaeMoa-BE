package org.silsagusi.joonggaemoa.api.agent.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.api.agent.application.dto.AgentDto;
import org.silsagusi.joonggaemoa.api.agent.application.dto.AgentUpdateRequest;
import org.silsagusi.joonggaemoa.api.agent.application.dto.UsernameDto;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.silsagusi.joonggaemoa.api.message.application.MessageTemplateService;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.silsagusi.joonggaemoa.global.auth.jwt.JwtProvider;
import org.silsagusi.joonggaemoa.global.auth.jwt.RefreshTokenStore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void signup(AgentDto.Request agentRequest) {

        if (agentRepository.existsByUsername(agentRequest.getUsername())) {
            throw new CustomException(ErrorCode.CONFLICT_USERNAME);
        }

        if (agentRepository.existsByPhone(agentRequest.getPhone())) {
            throw new CustomException(ErrorCode.CONFLICT_PHONE);
        }

        if (agentRepository.existsByEmail(agentRequest.getEmail())) {
            throw new CustomException(ErrorCode.CONFLICT_EMAIL);
        }

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

        agentRepository.save(agent);

        messageTemplateService.createDefaultMessageTemplate(agent);
    }

    public UsernameDto.Response getAgentByNameAndPhone(UsernameDto.Request usernameRequest) {
        Agent agent = agentRepository.findByNameAndPhone(usernameRequest.getName(), usernameRequest.getPhone())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return UsernameDto.Response.of(agent);
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

    public void updateAgent(Long agentId, AgentUpdateRequest agentUpdateRequest) {
        Agent agent = agentRepository.findById(agentId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        agent.updateAgent(agentUpdateRequest.getName(), agentUpdateRequest.getPhone(), agentUpdateRequest.getEmail(),
            agentUpdateRequest.getUsername(),
            agentUpdateRequest.getOffice(), agentUpdateRequest.getRegion(), agentUpdateRequest.getBusinessNo());

        agentRepository.save(agent);
    }
}