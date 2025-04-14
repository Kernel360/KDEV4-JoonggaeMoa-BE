package org.silsagusi.joonggaemoa.domain.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.customer.service.CustomerService;
import org.silsagusi.joonggaemoa.domain.customer.service.dto.CustomerDto;
import org.silsagusi.joonggaemoa.domain.customer.service.dto.CustomerSummaryResponse;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/api/customers")
    public ResponseEntity<ApiResponse<Void>> createCustomer(
        HttpServletRequest request,
        @RequestBody @Valid CustomerDto.Request customerRequestDto
    ) {
        customerService.createCustomer(
            (Long) request.getAttribute("agentId"),
            customerRequestDto
        );

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping(value = "/api/customers/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> bulkCreateCustomer(
        HttpServletRequest request,
        @RequestParam("file") MultipartFile file
    ) {
        customerService.bulkCreateCustomer(
            (Long) request.getAttribute("agentId"),
            file
        );

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/api/customers/bulk")
    public ResponseEntity<ApiResponse<String>> excelDownload() {
        String fileUploadResponse = customerService.excelDownload();
        return ResponseEntity.ok(ApiResponse.ok(fileUploadResponse));
    }

    @DeleteMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
        HttpServletRequest request,
        @PathVariable("customerId") Long customerId
    ) {
        customerService.deleteCustomer(
            (Long) request.getAttribute("agentId"),
            customerId
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PatchMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<Void>> updateCustomer(
        HttpServletRequest request,
        @PathVariable("customerId") Long customerId,
        @RequestBody @Valid CustomerDto.Request customerRequestDto
    ) {
        customerService.updateCustomer(
            (Long) request.getAttribute("agentId"),
            customerId,
            customerRequestDto
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/api/customers")
    public ResponseEntity<ApiResponse<Page<CustomerDto.Response>>> getAllCustomers(
        HttpServletRequest request,
        Pageable pageable
    ) {
        Page<CustomerDto.Response> customerResponseList = customerService.getAllCustomers(
            (Long) request.getAttribute("agentId"),
            pageable
        );

        return ResponseEntity.ok(ApiResponse.ok(customerResponseList));
    }

    @GetMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<CustomerDto.Response>> getCustomer(
        HttpServletRequest request,
        @PathVariable("customerId") Long customerId
    ) {
        CustomerDto.Response customerResponse = customerService.getCustomerById(
            (Long) request.getAttribute("agentId"),
            customerId
        );
        return ResponseEntity.ok(ApiResponse.ok(customerResponse));
    }

    @GetMapping("/api/dashboard/customer-summary")
    public ResponseEntity<ApiResponse<CustomerSummaryResponse>> getCustomerSummary(
        HttpServletRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
            customerService.getCustomerSummary(
                (Long) request.getAttribute("agentId")
            )
        ));
    }
}
