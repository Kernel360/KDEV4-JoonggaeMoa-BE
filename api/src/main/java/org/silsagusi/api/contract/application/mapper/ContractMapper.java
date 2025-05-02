package org.silsagusi.api.contract.application.mapper;

import org.silsagusi.api.contract.application.dto.CreateContractRequest;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {

	public Contract toEntity(
		CreateContractRequest contractRequest, Customer customerLandlord,
		Customer customerTenant, String filename
	) {
		return Contract.create(
			customerLandlord,
			customerTenant,
			contractRequest.getStartedAt(),
			contractRequest.getExpiredAt(),
			filename
		);
	}
}
