package org.silsagusi.joonggaemoa.domain.customer.controller.dto;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.customer.service.command.CustomerCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CustomerDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String name;
		private LocalDate birthday;
		private String phone;
		private String email;
		private String job;
		private Boolean isVip;
		private String memo;
		private Boolean consent;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateRequest {
		private String name;
		private LocalDate birthday;
		private String phone;
		private String email;
		private String job;
		private Boolean isVip;
		private String memo;
		private Boolean consent;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String name;
		private LocalDate birthday;
		private String phone;
		private String email;
		private String job;
		private Boolean isVip;
		private String memo;
		private Boolean consent;

		public static Response of(CustomerCommand customerCommand) {
			return Response.builder()
				.id(customerCommand.getId())
				.name(customerCommand.getName())
				.birthday(customerCommand.getBirthday())
				.phone(customerCommand.getPhone())
				.email(customerCommand.getEmail())
				.job(customerCommand.getJob())
				.isVip(customerCommand.getIsVip())
				.memo(customerCommand.getMemo())
				.consent(customerCommand.getConsent())
				.build();
		}
	}
}
