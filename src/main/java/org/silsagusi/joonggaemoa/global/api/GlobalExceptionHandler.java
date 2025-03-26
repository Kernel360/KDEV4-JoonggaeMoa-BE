package org.silsagusi.joonggaemoa.global.api;

import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

	// 존재하지 않는 요청에 대한 예외
	@ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
	public ApiResponse<?> handleNoPageFoundException(Exception e) {
		log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
		return ApiResponse.fail(new CustomException(ErrorCode.NOT_FOUND_END_POINT));
	}

	// 커스텀 예외
	@ExceptionHandler(value = {CustomException.class})
	public ApiResponse<?> handleCustomException(CustomException e) {
		log.error("GlobalExceptionHandler catch CustomException : {}", e.getMessage());
		return ApiResponse.fail(e);
	}

	// 기본 예외
	@ExceptionHandler(value = {Exception.class})
	public ApiResponse<?> handleException(Exception e) {
		log.error("GlobalExceptionHandler catch Exception : {}", e.getMessage());
		e.printStackTrace();
		return ApiResponse.fail(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}
