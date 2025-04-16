package org.silsagusi.core.customResponse;

import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ExceptionDto;
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
		return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, null, ExceptionDto.of(e.getErrorCode()));
	}
}
