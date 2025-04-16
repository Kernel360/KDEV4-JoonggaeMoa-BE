package org.silsagusi.joonggaemoa.core.customResponse.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}

	public Integer getCode() {
		return errorCode.getCode();
	}

	public HttpStatus getHttpStatus() {
		return errorCode.getHttpStatus();
	}
}
