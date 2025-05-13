package org.silsagusi.api.common.exception.handler;

import java.io.IOException;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		log.info("[CustomAccessDeniedHandler] :: {}", accessDeniedException.getMessage());
		log.info("[CustomAccessDeniedHandler] :: {}", request.getRequestURI());
		log.info("[CustomAccessDeniedHandler] :: 권한이 없음");

		CustomException customException = new CustomException(ErrorCode.FORBIDDEN);
		ApiResponse<Void> errorResponse = ApiResponse.fail(customException);

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.FORBIDDEN.value()); // 403 Unauthorized
		response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
	}
}
