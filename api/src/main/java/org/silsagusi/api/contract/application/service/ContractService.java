package org.silsagusi.api.contract.application.service;

import java.io.IOException;
import java.util.List;

import org.silsagusi.api.contract.application.dto.ContractDetailResponse;
import org.silsagusi.api.contract.application.dto.ContractResponse;
import org.silsagusi.api.contract.application.dto.ContractSummaryResponse;
import org.silsagusi.api.contract.application.dto.CreateContractRequest;
import org.silsagusi.api.contract.application.dto.ExpiredContractResponse;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContractService {

	private final ContractDataProvider contractDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final ContractMapper contractMapper;
	private final ContractValidator contractValidator;

	@Transactional
	public void createContract(CreateContractRequest contractRequest, MultipartFile file) throws IOException {
		Customer customerLandlord = customerDataProvider.getCustomer(contractRequest.getLandlordId());
		Customer customerTenant = customerDataProvider.getCustomer(contractRequest.getTenantId());

		String filename = contractDataProvider.fileUpload(file);

		Contract contract = contractMapper.toEntity(contractRequest, customerLandlord, customerTenant, filename);

		contractDataProvider.createContract(contract);
	}

	@Transactional(readOnly = true)
	public Page<ContractResponse> getAllContracts(Long agentId, Pageable pageable) {
		Page<ContractInfo> contractInfoPage = contractDataProvider.getAllContracts(agentId, pageable);
		return contractInfoPage.map(ContractResponse::toResponse);
	}

	@Transactional(readOnly = true)
	public ContractDetailResponse getContractById(Long agentId, String contractId) {
		Contract contract = contractDataProvider.getContract(contractId);
		contractValidator.validateAgentAccess(agentId, contract);

		ContractDetailInfo contractDetailInfo = contractDataProvider.getContractInfo(contract);
		return ContractDetailResponse.toResponse(contractDetailInfo);
	}

	@Transactional
	public void deleteContract(Long agentId, String contractId) {
		Contract contract = contractDataProvider.getContract(contractId);
		contractValidator.validateAgentAccess(agentId, contract);
		contractDataProvider.deleteContract(contract);
	}

	@Transactional(readOnly = true)
	public ContractSummaryResponse getContractSummary(Long agentId) {
		ContractSummaryInfo contractSummaryInfo = contractDataProvider.getSummary(agentId);
		return ContractSummaryResponse.toResponse(contractSummaryInfo);
	}

	@Transactional(readOnly = true)
	public ExpiredContractResponse getExpiredContract(Long agentId) {
		List<Contract> contracts = contractDataProvider.getExpiredContracts(agentId);
		return ExpiredContractResponse.toResponse(contracts);
	}
}