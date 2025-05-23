package org.silsagusi.api.common.exception.handler;

import java.io.IOException;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		log.info("[CustomAuthenticationEntryPointHandler] :: {}", authException.getMessage());
		log.info("[CustomAuthenticationEntryPointHandler] :: {}", request.getRequestURI());
		log.info("[CustomAuthenticationEntryPointHandler] :: 토큰 정보가 만료되었거나 존재하지 않음");

		CustomException customException =
			request.getAttribute("error") != null
				? (CustomException)request.getAttribute("error")
				: new CustomException(ErrorCode.UNAUTHORIZED);

		ApiResponse<Void> errorResponse = ApiResponse.fail(customException);

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 Unauthorized
		response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
	}
}
