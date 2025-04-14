package org.silsagusi.joonggaemoa.domain.contract.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;
import org.silsagusi.joonggaemoa.domain.contract.repository.ContractRepository;
import org.silsagusi.joonggaemoa.domain.contract.service.dto.ContractDetailDto;
import org.silsagusi.joonggaemoa.domain.contract.service.dto.ContractDto;
import org.silsagusi.joonggaemoa.domain.contract.service.dto.ContractSummaryResponse;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final AmazonS3 amazonS3;
    private static final String S3_BUCKET_NAME = "joonggaemoa";

    public void createContract(ContractDto.Request contractRequestDto, MultipartFile file) throws IOException {

        Customer customerLandlord = customerRepository.findById(contractRequestDto.getLandlordId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
        Customer customerTenant = customerRepository.findById(contractRequestDto.getTenantId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

        String filename = fileUpload(file);

        Contract contract = new Contract(
            customerLandlord,
            customerTenant,
            contractRequestDto.getCreatedAt(),
            contractRequestDto.getExpiredAt(),
            filename
        );
        contractRepository.save(contract);
    }

    public Page<ContractDto.Response> getAllContracts(Long agentId, Pageable pageable) {
        Page<Contract> contractPage = contractRepository.findAllByCustomerLandlord_AgentId(agentId, pageable);
        Page<ContractDto.Response> contractCommandPage = contractPage.map(ContractDto.Response::of);
        Page<ContractDto.Response> s3ContractCommandPage = contractCommandPage.map(
            it -> {
                try {
                    it.builder().url(getUrl(it.getUrl()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return it;
            });
        return s3ContractCommandPage;
    }

    public ContractDetailDto.Response getContractById(Long agentId, String contractId) throws IOException {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        if (!contract.getCustomerLandlord().getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        ContractDetailDto.Response contractDetailResponse = ContractDetailDto.Response.of(contract);
        contractDetailResponse.builder().url(contract.getUrl()).build();
        return contractDetailResponse;
    }

    public void deleteContract(Long agentId, String contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        if (!contract.getCustomerLandlord().getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        contractRepository.delete(contract);
    }

    private String fileUpload(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        //meta data
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        //S3 upload
        PutObjectRequest putObjectRequest = new PutObjectRequest("joonggaemoa", filename, file.getInputStream(),
            objectMetadata);
        amazonS3.putObject(putObjectRequest);

        return filename;
    }

    public String getUrl(String fileName) throws IOException {
        return amazonS3.getUrl(S3_BUCKET_NAME, fileName).toString();
    }

    public ContractSummaryResponse getContractSummary(Long agentId) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        long todayCount = contractRepository.countInProgress(agentId, today);
        long sevenDaysAgoCount = contractRepository.countInProgress(agentId, sevenDaysAgo);

        double rate;
        if (sevenDaysAgoCount == 0) {
            rate = todayCount == 0 ? 0 : 100;
        } else {
            rate = ((double) (todayCount - sevenDaysAgoCount) / sevenDaysAgoCount) * 100;
        }

        return new ContractSummaryResponse(todayCount, rate);
    }
}