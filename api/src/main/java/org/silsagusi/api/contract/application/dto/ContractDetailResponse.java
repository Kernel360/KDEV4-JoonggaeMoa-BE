package org.silsagusi.api.contract.application.dto;

import java.time.LocalDate;

import org.silsagusi.core.domain.contract.info.ContractDetailInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractDetailResponse {

	private String id;
	private Long landlordId;
	private Long tenantId;
	private String landlordName;
	private String tenantName;
	private String landlordPhone;
	private String tenantPhone;
	private String landlordEmail;
	private String tenantEmail;
	private LocalDate startedAt;
	private LocalDate expiredAt;
	private String url;

	public static ContractDetailResponse toResponse(ContractDetailInfo info) {
		return ContractDetailResponse.builder()
			.id(info.getId())
			.landlordId(info.getLandlordId())
			.tenantId(info.getTenantId())
			.landlordName(info.getLandlordName())
			.tenantName(info.getTenantName())
			.landlordPhone(info.getLandlordPhone())
			.tenantPhone(info.getTenantPhone())
			.landlordEmail(info.getLandlordEmail())
			.tenantEmail(info.getTenantEmail())
			.startedAt(info.getStartedAt())
			.expiredAt(info.getExpiredAt())
			.url(info.getUrl())
			.build();
	}
}
