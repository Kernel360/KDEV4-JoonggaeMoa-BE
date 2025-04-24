package org.silsagusi.api.inquiry.application.dto;

import org.silsagusi.core.domain.inquiry.entity.Inquiry;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InquiryDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "InquiryCreateRequestDto")
	public static class CreateRequest {

		@NotBlank
		private String name;

		@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
		@NotBlank
		private String password;

		@NotBlank
		private String title;

		@NotBlank
		private String content;

	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "InquiryUpdateRequestDto")
	public static class UpdateRequest {
		@NotBlank
		private String name;

		@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
		@NotBlank
		private String password;

		@NotBlank
		private String title;

		@NotBlank
		private String content;

	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String name;
		private String title;
		private String content;

		public static Response from(Inquiry inquiry) {
			return Response.builder()
				.id(inquiry.getId())
				.name(inquiry.getName())
				.title(inquiry.getTitle())
				.content(inquiry.getContent())
				.build();
		}
	}

	@Getter
	public static class PasswordRequest {
		@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
		@NotBlank
		private String password;
	}

}
