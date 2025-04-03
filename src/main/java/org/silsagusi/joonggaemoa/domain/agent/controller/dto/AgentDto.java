package org.silsagusi.joonggaemoa.domain.agent.controller.dto;

import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AgentDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		@NotBlank
		@Size(min = 4, max = 12, message = "아이디는 4~12자 사이여야 합니다.")
		private String username;

		@NotBlank
		@Size(min = 4, max = 12, message = "비밀번호는 4~12자 사이여야 합니다.")
		private String password;

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

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String name;
		private String phone;
		private String email;
		private String username;
		private String password;
		private String office;
		private String region;
		private String businessNo;
		private String role;

		public static Response of(AgentCommand agentCommand) {
			return AgentDto.Response.builder()
				.id(agentCommand.getId())
				.name(agentCommand.getName())
				.phone(agentCommand.getPhone())
				.email(agentCommand.getEmail())
				.username(agentCommand.getUsername())
				.password(agentCommand.getPassword())
				.office(agentCommand.getOffice())
				.region(agentCommand.getRegion())
				.businessNo(agentCommand.getBusinessNo())
				.role(agentCommand.getRole() + "")
				.build();
		}
	}
}
