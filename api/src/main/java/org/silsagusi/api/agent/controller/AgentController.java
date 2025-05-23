package org.silsagusi.api.agent.controller;

import org.silsagusi.api.agent.application.dto.AgentLoginRequest;
import org.silsagusi.api.agent.application.dto.AgentProfileResponse;
import org.silsagusi.api.agent.application.dto.AgentResponse;
import org.silsagusi.api.agent.application.dto.AgentSignUpRequest;
import org.silsagusi.api.agent.application.dto.FindUsername;
import org.silsagusi.api.agent.application.dto.UpdateAgentRequest;
import org.silsagusi.api.agent.application.service.AgentService;
import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AgentController {

	private final AgentService agentService;

	@PostMapping("/api/agents/signup")
	public ResponseEntity<ApiResponse<Void>> signup(
		@RequestBody @Valid AgentSignUpRequest agentSignUpRequest
	) {
		agentService.signup(agentSignUpRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping("/api/agents/me/username")
	public ResponseEntity<ApiResponse<FindUsername.Response>> findUsername(
		@RequestBody @Valid FindUsername.Request usernameRequest
	) {
		FindUsername.Response response = agentService.getAgentByNameAndPhone(usernameRequest);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@PostMapping("/api/agents/me/logout")
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

	@GetMapping("/api/agents/me")
	public ResponseEntity<ApiResponse<AgentResponse>> getAgent(@CurrentAgentId Long agentId) {
		AgentResponse response = agentService.getAgent(agentId);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@GetMapping("/api/agents/me/profile")
	public ResponseEntity<ApiResponse<AgentProfileResponse>> getAgentProfile(@CurrentAgentId Long agentId) {
		AgentProfileResponse response = agentService.getAgentProfile(agentId);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@PatchMapping("/api/agents/me")
	public ResponseEntity<ApiResponse<Void>> updateAgent(
		@CurrentAgentId Long agentId,
		@RequestBody @Valid UpdateAgentRequest updateAgentRequest
	) {
		agentService.updateAgent(agentId, updateAgentRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	// 스웨거용 더미 엔드포인트
	@PostMapping("/api/agents/login")
	public ResponseEntity<ApiResponse<Void>> login(@RequestBody AgentLoginRequest loginRequest) {
		return ResponseEntity.ok(ApiResponse.ok());
	}
}
