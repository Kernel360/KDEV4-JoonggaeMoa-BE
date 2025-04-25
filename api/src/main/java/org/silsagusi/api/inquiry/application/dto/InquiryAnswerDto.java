package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InquiryAnswerDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		@NotBlank
		private String content;
	}

	@Getter
	@Builder
	public static class Response {
		private String agentName;
		private String agentOffice;
		private String agentRegion;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
	}

	public static Response toResponse(InquiryAnswer inquiryAnswer) {
		return Response.builder()
			.agentName(inquiryAnswer.getAgent().getName())
			.agentOffice(inquiryAnswer.getAgent().getOffice())
			.agentRegion(inquiryAnswer.getAgent().getRegion())
			.content(inquiryAnswer.getContent())
			.createdAt(inquiryAnswer.getCreatedAt())
			.updatedAt(inquiryAnswer.getUpdatedAt())
			.build();
	}
}
