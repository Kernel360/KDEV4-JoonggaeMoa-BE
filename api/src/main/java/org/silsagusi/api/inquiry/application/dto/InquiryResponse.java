package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.inquiry.entity.Inquiry;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquiryResponse {
	private Long id;
	private String name;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<InquiryAnswerResponse> answers;

	public static InquiryResponse toResponse(Inquiry inquiry) {
		List<InquiryAnswerResponse> answers = inquiry.getAnswers()
			.stream()
			.map(InquiryAnswerResponse::toResponse)
			.toList();

		return InquiryResponse.builder()
			.id(inquiry.getId())
			.name(inquiry.getName())
			.title(inquiry.getTitle())
			.content(inquiry.getContent())
			.createdAt(inquiry.getCreatedAt())
			.updatedAt(inquiry.getUpdatedAt())
			.answers(answers)
			.build();
	}
}
