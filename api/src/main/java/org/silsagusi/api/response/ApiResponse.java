package org.silsagusi.api.response;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "data", "error"})
@AllArgsConstructor
public class ApiResponse<T> {

	boolean success;
	@Nullable
	T data;
	@Nullable
	ExceptionDto error;

	@ResponseStatus(HttpStatus.OK)
	public static <T> ApiResponse<T> ok() {
		return new ApiResponse<>(true, null, null);
	}

	@ResponseStatus(HttpStatus.OK)
	public static <T> ApiResponse<T> ok(final T data) {
		return new ApiResponse<>(true, data, null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static <T> ApiResponse<T> fail(final CustomException e) {
		return new ApiResponse<>(false, null, ExceptionDto.of(e.getErrorCode()));
	}
}
