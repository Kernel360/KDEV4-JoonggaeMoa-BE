package org.silsagusi.api.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode, Object... messageArgs) {
		super(errorCode.formatMessage(messageArgs));
		this.errorCode = errorCode;
	}

	public Integer getCode() {
		return errorCode.getCode();
	}
}
