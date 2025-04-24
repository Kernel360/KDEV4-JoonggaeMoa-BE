package org.silsagusi.api.contract.application.service;

import java.io.IOException;

import org.silsagusi.api.contract.application.dto.ContractDetailDto;
import org.silsagusi.api.contract.application.dto.ContractDto;
import org.silsagusi.api.contract.application.dto.ContractSummaryResponse;
import org.silsagusi.api.contract.application.mapper.ContractMapper;
import org.silsagusi.api.contract.application.validator.ContractValidator;
import org.silsagusi.api.contract.infrastructure.dataprovider.ContractDataProvider;
import org.silsagusi.api.customer.infrastructure.dataprovider.CustomerDataProvider;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.contract.info.ContractDetailInfo;
import org.silsagusi.core.domain.contract.info.ContractInfo;
import org.silsagusi.core.domain.contract.info.ContractSummaryInfo;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContractService {

	private final ContractDataProvider contractDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final ContractMapper contractMapper;
	private final ContractValidator contractValidator;

	public void createContract(ContractDto.Request contractRequest, MultipartFile file) throws IOException {
		Customer customerLandlord = customerDataProvider.getCustomer(contractRequest.getLandlordId());
		Customer customerTenant = customerDataProvider.getCustomer(contractRequest.getTenantId());

		String filename = contractDataProvider.fileUpload(file);

		Contract contract = contractMapper.toEntity(contractRequest, customerLandlord, customerTenant, filename);

		contractDataProvider.createContract(contract);
	}

	public Page<ContractDto.Response> getAllContracts(Long agentId, Pageable pageable) {
		Page<ContractInfo> contractInfoPage = contractDataProvider.getAllContracts(agentId, pageable);
		return contractInfoPage.map(contractMapper::toContractResponse);
	}

	public ContractDetailDto.Response getContractById(Long agentId, String contractId) throws IOException {
		Contract contract = contractDataProvider.getContract(contractId);
		contractValidator.validateAgentAccess(agentId, contract);

		ContractDetailInfo contractDetailInfo = contractDataProvider.getContractInfo(contract);
		return contractMapper.toContractDetailResponse(contractDetailInfo);
	}

	public void deleteContract(Long agentId, String contractId) {
		Contract contract = contractDataProvider.getContract(contractId);
		contractValidator.validateAgentAccess(agentId, contract);
		contractDataProvider.deleteContract(contract);
	}

	public ContractSummaryResponse getContractSummary(Long agentId) {
		ContractSummaryInfo contractSummaryInfo = contractDataProvider.getSummary(agentId);
		return contractMapper.toContractSummaryResponse(contractSummaryInfo);
	}
}