package org.silsagusi.api.inquiry.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInquiryAnswerRequest {
	@NotBlank
	private String content;
}
