package org.silsagusi.joonggaemoa.api.contract.domain.dataProvider;

import org.silsagusi.joonggaemoa.api.contract.domain.entity.Contract;
import org.silsagusi.joonggaemoa.api.contract.domain.info.ContractDetailInfo;
import org.silsagusi.joonggaemoa.api.contract.domain.info.ContractInfo;
import org.silsagusi.joonggaemoa.api.contract.domain.info.ContractSummaryInfo;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public interface ContractDataProvider {

    void createContract(Customer customerLandlord, Customer customerTenant, LocalDate createdAt, LocalDate expiredAt, MultipartFile file) throws IOException;

    Page<ContractInfo> getAllContracts(Long agentId, Pageable pageable);

    Contract getContract(String contractId);

    void validateAgentAccess(Long agentId, Contract contract);

    void deleteContract(Contract contract);

    ContractSummaryInfo getSummary(Long agentId);

    ContractDetailInfo getContractInfo(Contract contract) throws IOException;
}
