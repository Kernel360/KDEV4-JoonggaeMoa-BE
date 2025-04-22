package org.silsagusi.core.domain.contract.info;

import java.time.LocalDate;

import org.silsagusi.core.domain.contract.entity.Contract;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContractInfo {

	private String id;
	private String landlordName;
	private String tenantName;
	private LocalDate startedAt;
	private LocalDate expiredAt;
	private String url;

	public static ContractInfo of(Contract contract) {
		return ContractInfo.builder()
			.id(contract.getId())
			.landlordName(contract.getCustomerLandlord().getName())
			.tenantName(contract.getCustomerTenant().getName())
			.startedAt(contract.getStartedAt())
			.expiredAt(contract.getExpiredAt())
			.url(contract.getUrl())
			.build();
	}

}
