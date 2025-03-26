package org.silsagusi.joonggaemoa.domain.agent.controller;

import org.silsagusi.joonggaemoa.domain.agent.controller.dto.FindUsernameRequest;
import org.silsagusi.joonggaemoa.domain.agent.controller.dto.FindUsernameResponse;
import org.silsagusi.joonggaemoa.domain.agent.controller.dto.SignupRequest;
import org.silsagusi.joonggaemoa.domain.agent.service.AgentService;
import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/api/agent/signup")
	public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SignupRequest signupRequestDto) {
		agentService.signup(
			signupRequestDto.getUsername(),
			signupRequestDto.getPassword(),
			signupRequestDto.getName(),
			signupRequestDto.getPhone(),
			signupRequestDto.getEmail(),
			signupRequestDto.getOffice(),
			signupRequestDto.getRegion(),
			signupRequestDto.getBusinessNo());

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/agent/username")
	public ResponseEntity<ApiResponse<FindUsernameResponse>> getUsername(
		@RequestBody FindUsernameRequest findUsernameRequestDto) {
		AgentCommand agentCommand = agentService.getAgentByNameAndPhone(
			findUsernameRequestDto.getName(),
			findUsernameRequestDto.getPhone()
		);

		return ResponseEntity.ok(ApiResponse.ok(
			new FindUsernameResponse(agentCommand.getUsername())
		));
	}

	@PostMapping("/api/agent/logout")
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
