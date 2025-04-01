package org.silsagusi.joonggaemoa.global.auth.jwt;

import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final JwtProvider jwtProvider;
	private final RefreshTokenStore refreshTokenStore;

	public void refreshToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
		if (Boolean.FALSE.equals(jwtProvider.validateToken(refreshToken))) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		String username = jwtProvider.getSubject(refreshToken);

		String storedToken = refreshTokenStore.getRefreshToken(username);
		if (storedToken == null && !storedToken.equals(refreshToken)) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		Claims claims = jwtProvider.getClaims(storedToken);

		String accessToken = request.getHeader("Authorization").substring(7);
		Long id = Long.valueOf(jwtProvider.getTokenId(accessToken));

		String newAccessToken = jwtProvider.generateAccessToken(id, claims.get("username", String.class),
			claims.get("role", String.class));
		String newRefreshToken = jwtProvider.generateRefreshToken(username);

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
