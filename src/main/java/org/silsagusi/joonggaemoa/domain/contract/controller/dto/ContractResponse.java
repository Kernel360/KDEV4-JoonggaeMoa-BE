package org.silsagusi.joonggaemoa.domain.contract.controller.dto;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractResponse {
	private Long id;
	private Long landlordId;
	private Long tenantId;
	private LocalDate createdAt;
	private LocalDate expiredAt;
	private String url;

	public static ContractResponse of(ContractCommand command) {
		return ContractResponse.builder()
			.id(command.getId())
			.landlordId(command.getLandlordId())
			.tenantId(command.getTenantId())
			.createdAt(command.getCreatedAt())
			.expiredAt(command.getExpiredAt())
			.url(command.getUrl())
			.build();
	}
}
