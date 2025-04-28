package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.inquiry.command.UpdateInquiryCommand;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "InquiryConsultationRequestDto")
	public static class ConsultationRequest {

		@NotNull
		private Long agentId;

		@NotBlank
		private String name;

		@NotBlank
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		@NotBlank
		@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 000-0000-0000")
		private String phone;

		@NotNull
		private Boolean consent;

		@Future
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		private LocalDateTime consultAt;
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
