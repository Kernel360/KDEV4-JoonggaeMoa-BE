package org.silsagusi.joonggaemoa.domain.agent.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgentUpdateRequest {

	@NotBlank
	@Size(min = 4, max = 12, message = "아이디는 4~12자 사이여야 합니다.")
	private String username;

	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 000-0000-0000")
	private String phone;

	@NotBlank
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	private String office;
	private String region;
	private String businessNo;
}
