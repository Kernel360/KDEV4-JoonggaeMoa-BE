package org.silsagusi.joonggaemoa.api.contract.application.dto;

import lombok.Builder;
import lombok.Getter;

import org.silsagusi.joonggaemoa.core.domain.contract.info.ContractDetailInfo;

import java.time.LocalDate;

public class ContractDetailDto {

	@Getter
	@Builder
	public static class Response {
		private String id;
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

		public static Response of(ContractDetailInfo info) {
			return Response.builder()
				.id(info.getId())
				.landlordId(info.getLandlordId())
				.tenantId(info.getTenantId())
				.landlordName(info.getLandlordName())
				.tenantName(info.getLandlordName())
				.landlordPhone(info.getLandlordPhone())
				.tenantPhone(info.getTenantPhone())
				.landlordEmail(info.getLandlordEmail())
				.tenantEmail(info.getTenantEmail())
				.createdAt(info.getCreatedAt())
				.expiredAt(info.getExpiredAt())
				.url(info.getUrl())
				.build();
		}
	}
}
