package org.silsagusi.joonggaemoa.domain.contract.service.command;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ContractCommand {
	private Long id;
	private Long landlordId;
	private Long tenantId;
	private LocalDate createdAt;
	private LocalDate expiredAt;
	private String url;

	public static ContractCommand of(Contract contract) {
		return ContractCommand.builder()
			.id(contract.getId())
			.landlordId(contract.getCustomerLandlord().getId())
			.tenantId(contract.getCustomerTenant().getId())
			.createdAt(contract.getCreatedAt())
			.expiredAt(contract.getExpiredAt())
			.url(contract.getUrl())
			.build();
	}

}
