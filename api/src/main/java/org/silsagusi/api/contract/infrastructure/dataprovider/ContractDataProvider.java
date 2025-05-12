package org.silsagusi.api.contract.infrastructure.dataprovider;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.contract.infrastructure.repository.ContractRepository;
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

	public Page<ContractInfo> getAllContracts(Long agentId, String keyword, Pageable pageable) {
		Page<Contract> contractPage = contractRepository.findContracts(agentId, keyword, pageable);

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

		return new ContractSummaryInfo(todayCount, rate);
	}

	public ContractDetailInfo getContractInfo(Contract contract) {
		ContractDetailInfo contractDetailInfo = ContractDetailInfo.of(contract);
		contractDetailInfo.setUrl(getUrl(contract.getUrl()));
		return contractDetailInfo;
	}

	public String fileUpload(MultipartFile file) {
		String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

		//meta data
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(file.getContentType());
		objectMetadata.setContentLength(file.getSize());

		//S3 upload
		try {
			PutObjectRequest putObjectRequest =
				new PutObjectRequest(S3_BUCKET_NAME, filename, file.getInputStream(), objectMetadata);
			amazonS3.putObject(putObjectRequest);
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
		}

		return filename;
	}

	public String getUrl(String fileName) {
		return amazonS3.getUrl(S3_BUCKET_NAME, fileName).toString();
	}

	public List<Contract> getExpiredContracts(Long agentId) {
		LocalDate targetDate = LocalDate.now().plusMonths(6);
		return contractRepository.findAllByAgent_IdAndExpiredAtBeforeAndDeletedAtIsNull(agentId, targetDate);
	}
}