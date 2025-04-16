package org.silsagusi.core.domain.contract.info;

import lombok.Builder;
import lombok.Getter;

import org.silsagusi.core.domain.contract.entity.Contract;

import java.time.LocalDate;

@Getter
@Builder
public class ContractDetailInfo {
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

	public static ContractDetailInfo of(Contract contract) {
		return ContractDetailInfo.builder()
			.id(contract.getId())
			.landlordId(contract.getCustomerLandlord().getId())
			.tenantId(contract.getCustomerTenant().getId())
			.landlordName(contract.getCustomerLandlord().getName())
			.tenantName(contract.getCustomerTenant().getName())
			.landlordPhone(contract.getCustomerLandlord().getPhone())
			.tenantPhone(contract.getCustomerTenant().getPhone())
			.landlordEmail(contract.getCustomerLandlord().getEmail())
			.tenantEmail(contract.getCustomerTenant().getEmail())
			.createdAt(contract.getCreatedAt())
			.expiredAt(contract.getExpiredAt())
			.build();
	}
}
