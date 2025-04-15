package org.silsagusi.joonggaemoa.api.contract.application;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.silsagusi.joonggaemoa.api.contract.application.dto.ContractDetailDto;
import org.silsagusi.joonggaemoa.api.contract.application.dto.ContractDto;
import org.silsagusi.joonggaemoa.api.contract.application.dto.ContractSummaryResponse;
import org.silsagusi.joonggaemoa.api.contract.domain.dataProvider.ContractDataProvider;
import org.silsagusi.joonggaemoa.api.contract.domain.entity.Contract;
import org.silsagusi.joonggaemoa.api.contract.domain.info.ContractDetailInfo;
import org.silsagusi.joonggaemoa.api.contract.domain.info.ContractInfo;
import org.silsagusi.joonggaemoa.api.contract.domain.info.ContractSummaryInfo;
import org.silsagusi.joonggaemoa.api.contract.infrastructure.ContractRepository;
import org.silsagusi.joonggaemoa.api.customer.domain.Customer;
import org.silsagusi.joonggaemoa.api.customer.infrastructure.CustomerRepository;
import org.silsagusi.joonggaemoa.global.config.DataDBConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;

    private final ContractDataProvider contractDataProvider;
    private final DataDBConfig dataDBConfig;

    public void createContract(ContractDto.Request contractRequestDto, MultipartFile file) throws IOException {
        Customer customerLandlord = contractDataProvider.getCustomer(contractRequestDto.getLandlordId());
        Customer customerTenant = contractDataProvider.getCustomer(contractRequestDto.getTenantId());

        contractDataProvider.createContract(
            customerLandlord, customerTenant,
            contractRequestDto.getCreatedAt(), contractRequestDto.getExpiredAt(),
            file);
    }

    public Page<ContractDto.Response> getAllContracts(Long agentId, Pageable pageable) {
        Page<ContractInfo> contractInfoPage = contractDataProvider.getAllContracts(agentId, pageable);
        return contractInfoPage.map(ContractDto.Response::of);
    }

    public ContractDetailDto.Response getContractById(Long agentId, String contractId) throws IOException {
        Contract contract = contractDataProvider.getContract(contractId);
        contractDataProvider.validateAgentAccess(agentId, contract);

        ContractDetailInfo contractDetailInfo = contractDataProvider.getContractInfo(contract);
        ContractDetailDto.Response contractDetailResponse = ContractDetailDto.Response.of(contractDetailInfo);

        return contractDetailResponse;
    }

    public void deleteContract(Long agentId, String contractId) {
        Contract contract = contractDataProvider.getContract(contractId);
        contractDataProvider.validateAgentAccess(agentId, contract);
        contractDataProvider.deleteContract(contract);
    }

    public ContractSummaryResponse getContractSummary(Long agentId) {
        ContractSummaryInfo contractSummaryInfo = contractDataProvider.getSummary(agentId);
        return ContractSummaryResponse.of(contractSummaryInfo);
    }
}