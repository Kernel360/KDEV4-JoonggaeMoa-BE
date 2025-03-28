package org.silsagusi.joonggaemoa.global.api;

import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ExceptionDto;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "data", "error"})
@AllArgsConstructor
public class ApiResponse<T> {

	@JsonIgnore
	HttpStatus httpStatus;
	boolean success;
	@Nullable
	T data;
	@Nullable
	ExceptionDto error;

	public static <T> ApiResponse<T> ok() {
		return new ApiResponse<>(HttpStatus.OK, true, null, null);
	}

	public static <T> ApiResponse<T> ok(final T data) {
		return new ApiResponse<>(HttpStatus.OK, true, data, null);
	}

	public static <T> ApiResponse<T> created(@Nullable final T data) {
		return new ApiResponse<>(HttpStatus.CREATED, true, data, null);
	}

	public static <T> ApiResponse<T> fail(final CustomException e) {
		return new ApiResponse<>(e.getErrorCode().getHttpStatus(), false, null, ExceptionDto.of(e.getErrorCode()));
	}
}
