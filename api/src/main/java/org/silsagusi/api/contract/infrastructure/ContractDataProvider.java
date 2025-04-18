package org.silsagusi.api.contract.infrastructure;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.contract.info.ContractDetailInfo;
import org.silsagusi.core.domain.contract.info.ContractInfo;
import org.silsagusi.core.domain.contract.info.ContractSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContractDataProvider {

	private final ContractRepository contractRepository;

	private final AmazonS3 amazonS3;
	private static final String S3_BUCKET_NAME = "joonggaemoa";

	public void createContract(Contract contract) {
		contractRepository.save(contract);
	}

	public Page<ContractInfo> getAllContracts(Long agentId, Pageable pageable) {
		Page<Contract> contractPage = contractRepository.findAllByCustomerLandlord_AgentId(agentId, pageable);
		Page<ContractInfo> contractInfoPage = contractPage.map(ContractInfo::of);
		contractInfoPage.map(it -> {
			try {
				return it.builder().url(getUrl(it.getUrl()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		return contractInfoPage;
	}

	public Contract getContract(String contractId) {
		Contract contract = contractRepository.findById(contractId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
		return contract;
	}

	public void validateAgentAccess(Long agentId, Contract contract) {
		if (!contract.getCustomerLandlord().getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}

	public void deleteContract(Contract contract) {
		contractRepository.delete(contract);
	}

	public ContractSummaryInfo getSummary(Long agentId) {

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

		return ContractSummaryInfo.builder().count(todayCount).rate(rate).build();

	}

	public ContractDetailInfo getContractInfo(Contract contract) throws IOException {
		ContractDetailInfo contractDetailInfo = ContractDetailInfo.of(contract);
		contractDetailInfo.builder().url(getUrl(contractDetailInfo.getUrl()));
		return contractDetailInfo;
	}

	public String fileUpload(MultipartFile file) throws IOException {
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

}
