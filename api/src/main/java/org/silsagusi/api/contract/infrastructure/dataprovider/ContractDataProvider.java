package org.silsagusi.api.contract.infrastructure.dataprovider;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.silsagusi.api.contract.infrastructure.repository.ContractRepository;
import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
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

	private static final String S3_BUCKET_NAME = "joonggaemoa";
	private final ContractRepository contractRepository;
	private final AmazonS3 amazonS3;

	public void createContract(Contract contract) {
		contractRepository.save(contract);
	}

	public Page<ContractInfo> getAllContracts(Long agentId, Pageable pageable) {
		Page<Contract> contractPage = contractRepository.findAllByCustomerLandlord_AgentIdAndDeletedAtIsNull(agentId,
			pageable);
		Page<ContractInfo> contractInfoPage = contractPage.map(it -> {
			ContractInfo info = ContractInfo.of(it);
			info.setUrl(getUrl(it.getUrl()));
			return info;
		});
		return contractInfoPage;
	}

	public Contract getContract(String contractId) {
		Contract contract = contractRepository.findByIdAndDeletedAtIsNull(contractId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
		return contract;
	}

	public void deleteContract(Contract contract) {
		contract.markAsDeleted();
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

	public ContractDetailInfo getContractInfo(Contract contract) {
		ContractDetailInfo contractDetailInfo = ContractDetailInfo.of(contract);
		contractDetailInfo.setUrl(getUrl(contract.getUrl()));
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

	public String getUrl(String fileName) {
		return amazonS3.getUrl(S3_BUCKET_NAME, fileName).toString();
	}

}
