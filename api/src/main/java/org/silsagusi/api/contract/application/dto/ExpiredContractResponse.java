package org.silsagusi.api.contract.application.dto;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.core.domain.contract.entity.Contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpiredContractResponse {

	private List<ExpiredContract> expiredContracts;

	@Getter
	@Builder
	public static class ExpiredContract {
		private String id;
		private String landlordName;
		private String tenantName;
		private LocalDate expiredAt;
	}

	public static ExpiredContractResponse toResponse(List<Contract> contracts) {
		return new ExpiredContractResponse(
			contracts.stream()
				.map(contract ->
					ExpiredContract.builder()
						.id(contract.getId())
						.landlordName(contract.getCustomerLandlord().getName())
						.tenantName(contract.getCustomerTenant().getName())
						.expiredAt(contract.getExpiredAt())
						.build()
				).toList()
		);
	}
}
