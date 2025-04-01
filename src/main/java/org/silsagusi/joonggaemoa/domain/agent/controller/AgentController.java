package org.silsagusi.joonggaemoa.domain.agent.controller;

import org.silsagusi.joonggaemoa.domain.agent.controller.dto.AgentDto;
import org.silsagusi.joonggaemoa.domain.agent.controller.dto.FindUsernameDto;
import org.silsagusi.joonggaemoa.domain.agent.service.AgentService;
import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AgentController {

	private final AgentService agentService;

	@PostMapping("/api/agents/signup")
	public ResponseEntity<ApiResponse<Void>> signup(
		@RequestBody AgentDto.Request requestDto
	) {
		agentService.signup(
			requestDto.getUsername(),
			requestDto.getPassword(),
			requestDto.getName(),
			requestDto.getPhone(),
			requestDto.getEmail(),
			requestDto.getOffice(),
			requestDto.getRegion(),
			requestDto.getBusinessNo());

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping("/api/agents/username")
	public ResponseEntity<ApiResponse<FindUsernameDto.Response>> getUsername(
		@RequestBody FindUsernameDto.Request requestDto) {
		AgentCommand agentCommand = agentService.getAgentByNameAndPhone(
			requestDto.getName(),
			requestDto.getPhone()
		);

		return ResponseEntity.ok(ApiResponse.ok(
			FindUsernameDto.Response.of(agentCommand)
		));
	}

	@PostMapping("/api/agents/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
		agentService.logout(request.getHeader("Authorization").substring(7));

		Cookie refreshTokenCookie = new Cookie("refreshToken", null);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(0);
		response.addCookie(refreshTokenCookie);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}
