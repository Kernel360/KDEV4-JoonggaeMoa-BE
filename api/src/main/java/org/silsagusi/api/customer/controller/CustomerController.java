package org.silsagusi.api.customer.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.customer.application.dto.CreateCustomerRequest;
import org.silsagusi.api.customer.application.dto.CustomerHistoryResponse;
import org.silsagusi.api.customer.application.dto.CustomerResponse;
import org.silsagusi.api.customer.application.dto.CustomerSummaryResponse;
import org.silsagusi.api.customer.application.dto.UpdateCustomerRequest;
import org.silsagusi.api.customer.application.service.CustomerService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/api/customers")
	public ResponseEntity<ApiResponse<Void>> createCustomer(
		@CurrentAgentId Long agentId,
		@RequestBody @Valid CreateCustomerRequest customerRequestDto
	) {
		customerService.createCustomer(agentId, customerRequestDto);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping(value = "/api/customers/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Void>> bulkCreateCustomer(
		@CurrentAgentId Long agentId,
		@RequestParam("file") MultipartFile file
	) {
		customerService.bulkCreateCustomer(agentId, file);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/customers/bulk")
	public ResponseEntity<ApiResponse<String>> excelDownload() {
		String fileUploadResponse = customerService.excelDownload();
		return ResponseEntity.ok(ApiResponse.ok(fileUploadResponse));
	}

	@DeleteMapping("/api/customers/{customerId}")
	public ResponseEntity<ApiResponse<Void>> deleteCustomer(
		@CurrentAgentId Long agentId,
		@PathVariable("customerId") Long customerId
	) {
		customerService.deleteCustomer(agentId, customerId);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/customers/{customerId}")
	public ResponseEntity<ApiResponse<Void>> updateCustomer(
		@CurrentAgentId Long agentId,
		@PathVariable("customerId") Long customerId,
		@RequestBody @Valid UpdateCustomerRequest customerRequestDto
	) {
		customerService.updateCustomer(agentId, customerId, customerRequestDto);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/customers")
	public ResponseEntity<ApiResponse<Page<CustomerResponse>>> getAllCustomers(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<CustomerResponse> customerResponseList = customerService.getAllCustomers(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(customerResponseList));
	}

	@GetMapping("/api/customers/{customerId}")
	public ResponseEntity<ApiResponse<CustomerHistoryResponse>> getCustomer(
		@CurrentAgentId Long agentId,
		@PathVariable("customerId") Long customerId
	) {
		CustomerHistoryResponse customerResponse = customerService.getCustomerById(agentId, customerId);
		return ResponseEntity.ok(ApiResponse.ok(customerResponse));
	}

	@GetMapping("/api/dashboard/customer-summary")
	public ResponseEntity<ApiResponse<CustomerSummaryResponse>> getCustomerSummary(
		@CurrentAgentId Long agentId
	) {
		CustomerSummaryResponse customerSummaryResponse = customerService.getCustomerSummary(agentId);
		return ResponseEntity.ok(ApiResponse.ok(customerSummaryResponse));
	}
}
