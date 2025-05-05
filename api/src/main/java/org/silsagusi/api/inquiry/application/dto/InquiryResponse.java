package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;

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
	private Integer count;

	public static InquiryResponse toResponse(Inquiry inquiry) {
		return InquiryResponse.builder()
			.id(inquiry.getId())
			.name(inquiry.getName())
			.title(inquiry.getTitle())
			.content(inquiry.getContent())
			.createdAt(inquiry.getCreatedAt())
			.count(inquiry.getAnswers().size())
			.build();
	}
}
