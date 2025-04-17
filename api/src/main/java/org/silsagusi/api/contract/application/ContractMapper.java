package org.silsagusi.api.contract.application;

import org.silsagusi.api.contract.application.dto.ContractDto;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {

	public Contract toEntity(
		ContractDto.Request contractRequest, Customer customerLandlord,
		Customer customerTenant, String filename
	) {
		return Contract.create(
			customerLandlord,
			customerTenant,
			contractRequest.getCreatedAt(),
			contractRequest.getExpiredAt(),
			filename
		);
	}
}
