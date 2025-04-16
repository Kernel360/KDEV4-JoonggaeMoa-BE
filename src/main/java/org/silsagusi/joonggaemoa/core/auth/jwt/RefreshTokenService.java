package org.silsagusi.joonggaemoa.core.auth.jwt;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.silsagusi.joonggaemoa.core.customResponse.exception.CustomException;
import org.silsagusi.joonggaemoa.core.customResponse.exception.ErrorCode;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final JwtProvider jwtProvider;
	private final RefreshTokenStore refreshTokenStore;
	private final AgentRepository agentRepository;

	public void refreshToken(String refreshToken, HttpServletResponse response) {

		String username = jwtProvider.getSubject(refreshToken);

		String storedToken = refreshTokenStore.getRefreshToken(username);
		if (storedToken == null && !storedToken.equals(refreshToken)) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		Long agentId = Long.valueOf(jwtProvider.getTokenId(refreshToken));

		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

		String newAccessToken = jwtProvider.generateAccessToken(agent.getId(), agent.getUsername(),
			agent.getRole() + "");
		String newRefreshToken = jwtProvider.generateRefreshToken(agent.getId(), agent.getUsername());

		refreshTokenStore.deleteRefreshToken(refreshToken);
		refreshTokenStore.saveRefreshToken(username, newRefreshToken, jwtProvider.getExpirationTime(newRefreshToken));
		ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(86400)
			.build();

		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Authorization", "Bearer " + newAccessToken);
		response.addHeader("Set-Cookie", cookie.toString());
	}
}
