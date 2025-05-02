package org.silsagusi.api.inquiry.application.dto;

import org.silsagusi.core.domain.inquiry.command.UpdateInquiryCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInquiryRequest {
	@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
	@NotBlank
	private String password;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	public UpdateInquiryCommand toCommand() {
		return new UpdateInquiryCommand(password, title);
	}
}
