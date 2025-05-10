package org.silsagusi.core.domain.contract.info;

import java.time.LocalDate;

import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.customer.entity.Customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
	private LocalDate startedAt;
	private LocalDate expiredAt;
	@Setter
	private String url;

	public static ContractDetailInfo of(Contract contract) {
		Customer customerLandlord = contract.getCustomerLandlord();
		Customer customerTenant = contract.getCustomerTenant();

		return ContractDetailInfo.builder()
			.id(contract.getId())
			.landlordId(customerLandlord.getId())
			.tenantId(customerTenant.getId())
			.landlordName(customerLandlord.getName())
			.tenantName(customerTenant.getName())
			.landlordPhone(customerLandlord.getPhone())
			.tenantPhone(customerTenant.getPhone())
			.landlordEmail(customerLandlord.getEmail())
			.tenantEmail(customerTenant.getEmail())
			.startedAt(contract.getStartedAt())
			.expiredAt(contract.getExpiredAt())
			.build();
	}
}
