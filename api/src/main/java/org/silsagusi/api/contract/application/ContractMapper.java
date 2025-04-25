package org.silsagusi.api.contract.application;

import org.silsagusi.api.contract.application.dto.ContractDetailDto;
import org.silsagusi.api.contract.application.dto.ContractDto;
import org.silsagusi.api.contract.application.dto.ContractSummaryResponse;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.contract.info.ContractDetailInfo;
import org.silsagusi.core.domain.contract.info.ContractInfo;
import org.silsagusi.core.domain.contract.info.ContractSummaryInfo;
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

	public ContractDetailDto.Response toContractDetailResponse(ContractDetailInfo info) {
		return ContractDetailDto.Response.builder()
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

	public ContractDto.Response toContractResponse(ContractInfo info) {
		return ContractDto.Response.builder()
			.id(info.getId())
			.landlordName(info.getLandlordName())
			.tenantName(info.getTenantName())
			.createdAt(info.getCreatedAt())
			.expiredAt(info.getExpiredAt())
			.url(info.getUrl())
			.build();
	}

	public ContractSummaryResponse toContractSummaryResponse(ContractSummaryInfo info) {
		return ContractSummaryResponse.builder()
			.count(info.getCount())
			.rate(info.getRate())
			.build();
	}
}
