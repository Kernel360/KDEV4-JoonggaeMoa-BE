package org.silsagusi.api.message.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "MessageRequestDto")
	public static class Request {

		@NotBlank(message = "내용은 필수 값입니다.")
		private String content;

		@NotNull
		@FutureOrPresent
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		private LocalDateTime sendAt;

		private List<@NotNull Long> customerIdList;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private Long customerId;
		private String customerName;
		private String customerPhone;
		private String content;
		private LocalDateTime createdAt;
		private String sendStatus;
		private LocalDateTime sendAt;
	}
}
