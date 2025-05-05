package org.silsagusi.api.contract.controller;

import java.io.IOException;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.contract.application.dto.ContractDetailResponse;
import org.silsagusi.api.contract.application.dto.ContractResponse;
import org.silsagusi.api.contract.application.dto.ContractSummaryResponse;
import org.silsagusi.api.contract.application.dto.CreateContractRequest;
import org.silsagusi.api.contract.application.dto.ExpiredContractResponse;
import org.silsagusi.api.contract.application.service.ContractService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContractController {

	private final ContractService contractService;

	@PostMapping(value = "/api/contracts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<String>> createContract(
		@RequestPart("contractData") @Valid CreateContractRequest contractRequestDto,
		@RequestPart("file") MultipartFile file
	) throws IOException {
		contractService.createContract(contractRequestDto, file);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/contracts")
	public ResponseEntity<ApiResponse<Page<ContractResponse>>> getAllContracts(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<ContractResponse> contractResponsePage = contractService.getAllContracts(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(contractResponsePage));
	}

	@GetMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<ContractDetailResponse>> getContract(
		@CurrentAgentId Long agentId,
		@PathVariable("contractId") String contractId
	) {
		ContractDetailResponse contractResponse = contractService.getContractById(agentId, contractId);
		return ResponseEntity.ok(ApiResponse.ok(contractResponse));
	}

	@DeleteMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<Void>> deleteContract(
		@CurrentAgentId Long agentId,
		@PathVariable("contractId") String contractId
	) {
		contractService.deleteContract(agentId, contractId);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/dashboard/contract-summary")
	public ResponseEntity<ApiResponse<ContractSummaryResponse>> getContractSummary(
		@CurrentAgentId Long agentId
	) {
		ContractSummaryResponse contractSummaryResponse = contractService.getContractSummary(agentId);
		return ResponseEntity.ok(ApiResponse.ok(contractSummaryResponse));
	}

	@GetMapping("/api/dashboard/expired-contract")
	public ResponseEntity<ApiResponse<ExpiredContractResponse>> getExpiredContract(
		@CurrentAgentId Long agentId
	) {
		ExpiredContractResponse expiredContractResponse = contractService.getExpiredContract(agentId);
		return ResponseEntity.ok(ApiResponse.ok(expiredContractResponse));
	}
}
