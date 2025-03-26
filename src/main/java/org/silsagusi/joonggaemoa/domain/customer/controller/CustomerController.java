package org.silsagusi.joonggaemoa.domain.customer.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.controller.dto.CreateCustomerRequest;
import org.silsagusi.joonggaemoa.domain.customer.controller.dto.CustomerResponse;
import org.silsagusi.joonggaemoa.domain.customer.controller.dto.UpdateCustomerRequest;
import org.silsagusi.joonggaemoa.domain.customer.service.CustomerService;
import org.silsagusi.joonggaemoa.domain.customer.service.command.CustomerCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/api/agents/{agentId}/customers")
	public ResponseEntity<ApiResponse<Void>> createCustomer(
		@PathVariable("agentId") Long agentId,
		@RequestBody CreateCustomerRequest createCustomerRequestDto
	) {
		customerService.createCustomer(
			agentId,
			createCustomerRequestDto.getName(),
			createCustomerRequestDto.getBirthday(),
			createCustomerRequestDto.getPhone(),
			createCustomerRequestDto.getEmail(),
			createCustomerRequestDto.getJob(),
			createCustomerRequestDto.getIsVip(),
			createCustomerRequestDto.getMemo(),
			createCustomerRequestDto.getConsent()
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping(value = "/api/agents/{agentId}/customers/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Void>> bulkCreateCustomer(
		@PathVariable("agentId") Long agentId,
		@RequestParam("file") MultipartFile file
	) {
		//TODO: service<->controller command dto
		customerService.bulkCreateCustomer(agentId, file);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/agents/{agentId}/customers/{customerId}")
	public ResponseEntity<ApiResponse<Void>> deleteCustomer(
		@PathVariable("customerId") Long customerId
	) {
		customerService.deleteCustomer(customerId);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/agents/{agentId}/customers/{customerId}")
	public ResponseEntity<ApiResponse<Void>> updateCustomer(
		@PathVariable("agentId") Long agentId,
		@PathVariable("customerId") Long customerId,
		@RequestBody UpdateCustomerRequest updateCustomerRequest
	) {
		customerService.updateCustomer(
			customerId,
			updateCustomerRequest.getName(),
			updateCustomerRequest.getBirthday(),
			updateCustomerRequest.getPhone(),
			updateCustomerRequest.getEmail(),
			updateCustomerRequest.getJob(),
			updateCustomerRequest.getIsVip(),
			updateCustomerRequest.getMemo(),
			updateCustomerRequest.getConsent()
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/agents/{agentId}/customers")
	public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
		List<CustomerCommand> customerCommandList = customerService.getAllCustomers();
		List<CustomerResponse> customerResponseList = customerCommandList.stream()
			.map(it -> CustomerResponse.of(it)).toList();

		return ResponseEntity.ok(ApiResponse.ok(customerResponseList));
	}

	@GetMapping("/api/agents/{agentId}/customers/{customerId}")
	public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
		@PathVariable("customerId") Long customerId
	) {
		CustomerCommand customerCommand = customerService.getCustomerById(customerId);

		return ResponseEntity.ok(ApiResponse.ok(CustomerResponse.of(customerCommand)));
	}

}
