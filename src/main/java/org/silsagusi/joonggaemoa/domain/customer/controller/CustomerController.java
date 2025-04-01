package org.silsagusi.joonggaemoa.domain.customer.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.controller.dto.CustomerDto;
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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/api/customers")
	public ResponseEntity<ApiResponse<Void>> createCustomer(
		HttpServletRequest request,
		@RequestBody CustomerDto.Request requestDto
	) {
		customerService.createCustomer(
			(Long)request.getAttribute("agentId"),
			requestDto.getName(),
			requestDto.getBirthday(),
			requestDto.getPhone(),
			requestDto.getEmail(),
			requestDto.getJob(),
			requestDto.getIsVip(),
			requestDto.getMemo(),
			requestDto.getConsent()
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping(value = "/api/customers/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Void>> bulkCreateCustomer(
		HttpServletRequest request,
		@RequestParam("file") MultipartFile file
	) {
		//TODO: service<->controller command dto
		customerService.bulkCreateCustomer(
			(Long)request.getAttribute("agentId"),
			file
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/customers/{customerId}")
	public ResponseEntity<ApiResponse<Void>> deleteCustomer(
		@PathVariable("customerId") Long customerId
	) {
		customerService.deleteCustomer(customerId);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/customers/{customerId}")
	public ResponseEntity<ApiResponse<Void>> updateCustomer(
		@PathVariable("customerId") Long customerId,
		@RequestBody CustomerDto.Request requestDto
	) {
		customerService.updateCustomer(
			customerId,
			requestDto.getName(),
			requestDto.getBirthday(),
			requestDto.getPhone(),
			requestDto.getEmail(),
			requestDto.getJob(),
			requestDto.getIsVip(),
			requestDto.getMemo(),
			requestDto.getConsent()
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/customers")
	public ResponseEntity<ApiResponse<List<CustomerDto.Response>>> getAllCustomers() {
		List<CustomerCommand> customerCommandList = customerService.getAllCustomers();
		List<CustomerDto.Response> customerResponseList = customerCommandList.stream()
			.map(CustomerDto.Response::of).toList();

		return ResponseEntity.ok(ApiResponse.ok(customerResponseList));
	}

	@GetMapping("/api/customers/{customerId}")
	public ResponseEntity<ApiResponse<CustomerDto.Response>> getCustomer(
		@PathVariable("customerId") Long customerId
	) {
		CustomerCommand customerCommand = customerService.getCustomerById(customerId);

		return ResponseEntity.ok(ApiResponse.ok(CustomerDto.Response.of(customerCommand)));
	}

}
