package org.silsagusi.api.exception.handler;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	private ApiResponse<?> fail(ErrorCode errorCode) {
		return ApiResponse.fail(new CustomException(errorCode));
	}

	// 존재하지 않는 요청에 대한 예외
	@ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
	public ApiResponse<?> handleNoPageFoundException(Exception e) {
		log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
		return fail(ErrorCode.NOT_FOUND_END_POINT);
	}

	// 커스텀 예외
	@ExceptionHandler(value = {CustomException.class})
	public ApiResponse<?> handleCustomException(CustomException e) {
		//log.error("GlobalExceptionHandler catch CustomException : {}", e.getMessage(), e);
		log.error("GlobalExceptionHandler catch CustomException : {}", e.getMessage());
		return fail(e.getErrorCode());
	}

	// 기본 예외
	@ExceptionHandler(value = {Exception.class})
	public ApiResponse<?> handleException(Exception e) {
		//log.error("\u001B[31mGlobalExceptionHandler catch Exception : {}\u001B[0m", e.getMessage(), e);
		log.error("GlobalExceptionHandler catch CustomException : {}", e.getMessage());
		return fail(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = {DataIntegrityViolationException.class})
	public ApiResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("GlobalExceptionHandler catch DataIntegrityViolationException : {}", e.getMessage());
		String message = e.getMostSpecificCause().getMessage();

		if (message.contains("UK_agent_username")) {
			return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT_USERNAME));
		} else if (message.contains("UK_agent_phone")) {
			return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT_PHONE));
		} else if (message.contains("UK_agent_email")) {
			return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT_EMAIL));
		}

		return fail(ErrorCode.CONFLICT);
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("GlobalExceptionHandler catch MethodArgumentNotValidException : {}", e.getMessage());
		return fail(ErrorCode.VALIDATION_FAILED);
	}
}
