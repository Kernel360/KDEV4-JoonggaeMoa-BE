package org.silsagusi.joonggaemoa.domain.contract.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.contract.controller.dto.ContractSummaryResponse;
import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;
import org.silsagusi.joonggaemoa.domain.contract.repository.ContractRepository;
import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {

	private final ContractRepository contractRepository;
	private final CustomerRepository customerRepository;
	private final AgentRepository agentRepository;
	private final AmazonS3 amazonS3;
	private static final String S3_BUCKET_NAME = "joonggaemoa";

	public void createContract(
		Long landlordId,
		Long tenantId,
		LocalDate createdAt,
		LocalDate expiredAt,
		MultipartFile file
	) throws IOException {

		Customer customerLandlord = customerRepository.findById(landlordId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
		Customer customerTenant = customerRepository.findById(tenantId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

		String filename = fileUpload(file);

		Contract contract = new Contract(
			customerLandlord,
			customerTenant,
			createdAt,
			expiredAt,
			filename
		);
		contractRepository.save(contract);
	}

	public Page<ContractCommand> getAllContracts(Long agentId, Pageable pageable) {
		Page<Contract> contractPage = contractRepository.findAllByCustomerLandlord_AgentId(agentId, pageable);
		Page<ContractCommand> contractCommandPage = contractPage.map(ContractCommand::of);
		Page<ContractCommand> s3ContractCommandPage = contractCommandPage.map(
			it -> {
				try {
					it.setUrl(getUrl(it.getUrl()));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return it;
			});
		return s3ContractCommandPage;
	}

	public ContractCommand getContractById(Long agentId, Long contractId) throws IOException {
		Contract contract = contractRepository.findById(contractId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!contract.getCustomerLandlord().getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}

		ContractCommand command = ContractCommand.of(contract);
		command.setUrl(getUrl(command.getUrl()));
		return command;
	}

	public void deleteContract(Long agentId, Long contractId) {
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
			rate = ((double)(todayCount - sevenDaysAgoCount) / sevenDaysAgoCount) * 100;
		}

		return new ContractSummaryResponse(todayCount, rate);
	}
}