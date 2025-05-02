package org.silsagusi.api.contract.application.dto;

import java.time.LocalDate;

import org.silsagusi.core.domain.contract.info.ContractInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractResponse {
	private String id;
	private String landlordName;
	private String tenantName;
	private LocalDate startedAt;
	private LocalDate expiredAt;

	public static ContractResponse toResponse(ContractInfo info) {
		return ContractResponse.builder()
			.id(info.getId())
			.landlordName(info.getLandlordName())
			.tenantName(info.getTenantName())
			.startedAt(info.getStartedAt())
			.expiredAt(info.getExpiredAt())
			.build();
	}
}
