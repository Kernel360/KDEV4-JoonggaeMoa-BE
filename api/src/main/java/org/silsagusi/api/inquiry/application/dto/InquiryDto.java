package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.inquiry.command.UpdateInquiryCommand;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
	public static class Response {
		private Long id;
		private String name;
		private String title;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private List<InquiryAnswerDto.Response> answers;
	}

	@Getter
	public static class PasswordRequest {
		@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
		@NotBlank
		private String password;
	}

	public static Response toResponse(Inquiry inquiry) {
		List<InquiryAnswerDto.Response> answers = inquiry.getAnswers()
			.stream()
			.map(InquiryAnswerDto::toResponse)
			.toList();

		return Response.builder()
			.id(inquiry.getId())
			.name(inquiry.getName())
			.title(inquiry.getTitle())
			.content(inquiry.getContent())
			.createdAt(inquiry.getCreatedAt())
			.updatedAt(inquiry.getUpdatedAt())
			.answers(answers)
			.build();
	}

	public static UpdateInquiryCommand toCommand(UpdateRequest inquiryUpdateRequest) {
		return UpdateInquiryCommand.builder()
			.title(inquiryUpdateRequest.getTitle())
			.content(inquiryUpdateRequest.getContent())
			.build();
	}
}
