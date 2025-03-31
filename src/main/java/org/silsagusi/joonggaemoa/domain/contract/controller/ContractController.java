package org.silsagusi.joonggaemoa.domain.contract.controller;

import java.io.IOException;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.contract.controller.dto.ContractResponse;
import org.silsagusi.joonggaemoa.domain.contract.controller.dto.CreateContractRequest;
import org.silsagusi.joonggaemoa.domain.contract.controller.dto.UpdateContractRequest;
import org.silsagusi.joonggaemoa.domain.contract.service.ContractService;
import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContractController {

	private final ContractService contractService;

	@PostMapping(value = "/api/contracts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<String>> createContract(
		@RequestPart("contractData") CreateContractRequest createContractRequest,
		@RequestPart("file") MultipartFile file
	) throws IOException {
		contractService.createContract(
			createContractRequest.getLandlordId(),
			createContractRequest.getTenantId(),
			createContractRequest.getCreatedAt(),
			createContractRequest.getExpiredAt(),
			file
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/contracts")
	public ResponseEntity<ApiResponse<List<ContractResponse>>> getAllContracts() {
		List<ContractCommand> contractCommandList = contractService.getAllContracts();
		List<ContractResponse> contractResponseList = contractCommandList.stream()
			.map(ContractResponse::of).toList();
		return ResponseEntity.ok(ApiResponse.ok(contractResponseList));
	}

	@GetMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<ContractResponse>> getContract(
		@PathVariable("contractId") Long contractId
	) throws IOException {
		ContractCommand contractCommand = contractService.getContractById(contractId);
		return ResponseEntity.ok(ApiResponse.ok(ContractResponse.of(contractCommand)));
	}

	@PatchMapping(value = "/api/contracts/{contractId}",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Void>> updateContract(
		@PathVariable("contractId") Long contractId,
		@RequestPart("contractData") UpdateContractRequest updateContractRequest,
		@RequestPart("file") MultipartFile file
	) throws IOException {
		contractService.updateContract(
			contractId,
			updateContractRequest.getCreatedAt(),
			updateContractRequest.getExpiredAt(),
			file
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<Void>> deleteContract(
		@PathVariable("contractId") Long contractId
	) {
		contractService.deleteContract(contractId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}
