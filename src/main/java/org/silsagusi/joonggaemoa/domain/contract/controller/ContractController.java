package org.silsagusi.joonggaemoa.domain.contract.controller;

<<<<<<< HEAD
import jakarta.validation.Valid;
=======
>>>>>>> 67dab90192af1b57f835acb05e5a73a389e3f132
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.contract.controller.dto.ContractDto;
import org.silsagusi.joonggaemoa.domain.contract.service.ContractService;
import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
<<<<<<< HEAD

=======
>>>>>>> 67dab90192af1b57f835acb05e5a73a389e3f132

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

<<<<<<< HEAD

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
    public ResponseEntity<ApiResponse<ContractDto.Response>> getContract(
            @PathVariable("contractId") Long contractId
    ) throws IOException {
        ContractCommand contractCommand = contractService.getContractById(contractId);
        return ResponseEntity.ok(ApiResponse.ok(ContractDto.Response.of(contractCommand)));
    }

=======
    @PostMapping(value = "/api/contracts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> createContract(
            @RequestPart("contractData") ContractDto.Request requestDto,
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
    public ResponseEntity<ApiResponse<ContractDto.Response>> getContract(
            @PathVariable("contractId") Long contractId
    ) throws IOException {
        ContractCommand contractCommand = contractService.getContractById(contractId);
        return ResponseEntity.ok(ApiResponse.ok(ContractDto.Response.of(contractCommand)));
    }

>>>>>>> 67dab90192af1b57f835acb05e5a73a389e3f132
    @DeleteMapping("/api/contracts/{contractId}")
    public ResponseEntity<ApiResponse<Void>> deleteContract(
            @PathVariable("contractId") Long contractId
    ) {
        contractService.deleteContract(contractId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
