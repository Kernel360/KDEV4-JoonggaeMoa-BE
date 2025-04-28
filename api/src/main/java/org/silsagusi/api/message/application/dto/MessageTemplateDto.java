package org.silsagusi.api.message.application.dto;

import java.util.List;

import org.silsagusi.core.domain.message.entity.MessageTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

	public static List<Response> toResponseList(List<MessageTemplate> messageTemplates) {
		return messageTemplates.stream()
			.map(messageTemplate ->
				MessageTemplateDto.Response.builder()
					.id(messageTemplate.getId())
					.title(messageTemplate.getTitle())
					.content(messageTemplate.getContent())
					.build()
			).toList();
	}
}
