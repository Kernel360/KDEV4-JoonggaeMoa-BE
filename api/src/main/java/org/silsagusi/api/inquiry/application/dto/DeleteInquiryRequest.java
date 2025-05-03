package org.silsagusi.api.inquiry.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteInquiryRequest {
	@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
	@NotBlank
	private String password;
}
