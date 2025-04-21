package org.silsagusi.api.contract.controller;

import java.io.IOException;

import org.silsagusi.api.contract.application.ContractService;
import org.silsagusi.api.contract.application.dto.ContractDetailDto;
import org.silsagusi.api.contract.application.dto.ContractDto;
import org.silsagusi.api.contract.application.dto.ContractSummaryResponse;
import org.silsagusi.api.customResponse.ApiResponse;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContractController {

	private final ContractService contractService;

	@PostMapping(value = "/api/contracts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<String>> createContract(
		@RequestPart("contractData") @Valid ContractDto.Request contractRequestDto,
		@RequestPart("file") MultipartFile file
	) throws IOException {
		contractService.createContract(contractRequestDto, file);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/contracts")
	public ResponseEntity<ApiResponse<Page<ContractDto.Response>>> getAllContracts(
		HttpServletRequest request,
		Pageable pageable
	) {
		Page<ContractDto.Response> contractResponsePage = contractService.getAllContracts(
			(Long)request.getAttribute("agentId"),
			pageable
		);
		return ResponseEntity.ok(ApiResponse.ok(contractResponsePage));
	}

	@GetMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<ContractDetailDto.Response>> getContract(
		HttpServletRequest request,
		@PathVariable("contractId") String contractId
	) throws IOException {
		ContractDetailDto.Response contractResponse = contractService.getContractById(
			(Long)request.getAttribute("agentId"),
			contractId
		);
		return ResponseEntity.ok(ApiResponse.ok(contractResponse));
	}

	@DeleteMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<Void>> deleteContract(
		HttpServletRequest request,
		@PathVariable("contractId") String contractId
	) {
		contractService.deleteContract(
			(Long)request.getAttribute("agentId"),
			contractId
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/dashboard/contract-summary")
	public ResponseEntity<ApiResponse<ContractSummaryResponse>> getContractSummary(
		HttpServletRequest request
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			contractService.getContractSummary(
				(Long)request.getAttribute("agentId")
			)
		));
	}
}
