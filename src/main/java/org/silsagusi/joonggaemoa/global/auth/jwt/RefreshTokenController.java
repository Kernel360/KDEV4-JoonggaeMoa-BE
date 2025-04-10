package org.silsagusi.joonggaemoa.global.auth.jwt;

import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

	private final RefreshTokenService refreshTokenService;

	@PostMapping("/api/refresh-token")
	public ResponseEntity<ApiResponse<Void>> refresh(HttpServletRequest request, HttpServletResponse response) {
		log.info("Refreshing token");
		Cookie refreshToken = WebUtils.getCookie(request, "refreshToken");
		log.info("Refresh token: {}", refreshToken);
		if (refreshToken == null && refreshToken.getValue().isEmpty()) {
			return ResponseEntity.status(401).body(ApiResponse.fail(new CustomException(ErrorCode.UNAUTHORIZED)));
		}

		refreshTokenService.refreshToken(refreshToken.getValue(), response);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}
