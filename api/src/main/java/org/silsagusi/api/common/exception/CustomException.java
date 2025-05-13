package org.silsagusi.api.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;
	private final Class<?> beanType;

	public CustomException(ErrorCode errorCode, Object... messageArgs) {
		this(errorCode, null, messageArgs);
	}

	public CustomException(ErrorCode errorCode, Class<?> beanType, Object... messageArgs) {
		super(errorCode.formatMessage(messageArgs));
		this.errorCode = errorCode;
		this.beanType = beanType;
	}
}
