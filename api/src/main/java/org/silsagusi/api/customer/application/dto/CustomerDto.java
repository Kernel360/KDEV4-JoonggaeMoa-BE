package org.silsagusi.api.customer.application.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CustomerDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "CustomerRequestDto")
	public static class Request {

		@NotBlank
		private String name;

		@NotNull
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate birthday;

		@NotBlank
		@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 000-0000-0000")
		private String phone;

		@NotBlank
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		private String job;

		@NotNull
		private Boolean isVip;

		private String memo;

		@NotNull
		private Boolean consent;

		private String interestProperty;

		private String interestLocation;

		private String assetStatus;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateRequest {
		private String name;
		private LocalDate birthday;
		private String phone;
		private String email;
		private String job;
		private Boolean isVip;
		private String memo;
		private Boolean consent;
		private String interestProperty;
		private String interestLocation;
		private String assetStatus;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String name;
		private LocalDate birthday;
		private String phone;
		private String email;
		private String job;
		private Boolean isVip;
		private String memo;
		private Boolean consent;
		private String interestProperty;
		private String interestLocation;
		private String assetStatus;
	}
}
