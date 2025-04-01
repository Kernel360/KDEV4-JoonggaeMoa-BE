package org.silsagusi.joonggaemoa.domain.contract.controller.dto;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContractDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private Long landlordId;
		private Long tenantId;
		private LocalDate createdAt;
		private LocalDate expiredAt;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private Long landlordId;
		private Long tenantId;
		private LocalDate createdAt;
		private LocalDate expiredAt;
		private String url;

		public static Response of(ContractCommand command) {
			return Response.builder()
				.id(command.getId())
				.landlordId(command.getLandlordId())
				.tenantId(command.getTenantId())
				.createdAt(command.getCreatedAt())
				.expiredAt(command.getExpiredAt())
				.url(command.getUrl())
				.build();
		}
	}
}
