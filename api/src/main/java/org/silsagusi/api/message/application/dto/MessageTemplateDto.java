package org.silsagusi.api.message.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageTemplateDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "MessageTemplateRequestDto")
	public static class Request {

		@NotBlank
		private String title;

		@NotBlank
		private String content;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String title;
		private String content;
	}
}
