package org.silsagusi.joonggaemoa.domain.contract.controller;

import java.io.IOException;

import org.silsagusi.joonggaemoa.domain.contract.controller.dto.ContractDetailDto;
import org.silsagusi.joonggaemoa.domain.contract.controller.dto.ContractDto;
import org.silsagusi.joonggaemoa.domain.contract.service.ContractService;
import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
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
		@RequestPart("contractData") @Valid ContractDto.Request requestDto,
		@RequestPart("file") MultipartFile file
	) throws IOException {
		contractService.createContract(
			requestDto.getLandlordId(),
			requestDto.getTenantId(),
			requestDto.getCreatedAt(),
			requestDto.getExpiredAt(),
			file
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/contracts")
	public ResponseEntity<ApiResponse<Page<ContractDto.Response>>> getAllContracts(Pageable pageable) {
		Page<ContractCommand> contractCommandPage = contractService.getAllContracts(pageable);
		Page<ContractDto.Response> contractResponsePage = contractCommandPage.map(ContractDto.Response::of);
		return ResponseEntity.ok(ApiResponse.ok(contractResponsePage));
	}

	@GetMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<ContractDetailDto.Response>> getContract(
		@PathVariable("contractId") Long contractId
	) throws IOException {
		ContractCommand contractCommand = contractService.getContractById(contractId);
		return ResponseEntity.ok(ApiResponse.ok(ContractDetailDto.Response.of(contractCommand)));
	}

	@DeleteMapping("/api/contracts/{contractId}")
	public ResponseEntity<ApiResponse<Void>> deleteContract(
		@PathVariable("contractId") Long contractId
	) {
		contractService.deleteContract(contractId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}
