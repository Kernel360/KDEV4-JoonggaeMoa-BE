package org.silsagusi.joonggaemoa.domain.contract.controller.dto;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;

import lombok.Builder;
import lombok.Getter;

public class ContractDetailDto {

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private Long landlordId;
		private Long tenantId;
		private String landlordName;
		private String tenantName;
		private String landlordPhone;
		private String tenantPhone;
		private String landlordEmail;
		private String tenantEmail;
		private LocalDate createdAt;
		private LocalDate expiredAt;
		private String url;

		public static Response of(ContractCommand command) {
			return Response.builder()
				.id(command.getId())
				.landlordId(command.getLandlordId())
				.tenantId(command.getTenantId())
				.landlordName(command.getLandlordName())
				.tenantName(command.getTenantName())
				.landlordPhone(command.getLandlordPhone())
				.tenantPhone(command.getTenantPhone())
				.landlordEmail(command.getLandlordEmail())
				.tenantEmail(command.getTenantEmail())
				.createdAt(command.getCreatedAt())
				.expiredAt(command.getExpiredAt())
				.url(command.getUrl())
				.build();
		}
	}
}
