package org.silsagusi.joonggaemoa.domain.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.customer.controller.dto.CustomerDto;
import org.silsagusi.joonggaemoa.domain.customer.service.CustomerService;
import org.silsagusi.joonggaemoa.domain.customer.service.command.CustomerCommand;
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
            @RequestBody @Valid CustomerDto.Request requestDto
    ) {
        customerService.createCustomer(
                (Long) request.getAttribute("agentId"),
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
        customerService.bulkCreateCustomer(
                (Long) request.getAttribute("agentId"),
                file
        );

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/api/customers/builk")
    public ResponseEntity<ApiResponse<String>> excelDownload() {
        String fileUploadResponse = customerService.excelDownload();
        return ResponseEntity.ok(ApiResponse.ok(fileUploadResponse));
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
            @RequestBody @Valid CustomerDto.Request requestDto
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
    public ResponseEntity<ApiResponse<Page<CustomerDto.Response>>> getAllCustomers(Pageable pageable) {
        Page<CustomerCommand> customerCommandList = customerService.getAllCustomers(pageable);
        Page<CustomerDto.Response> customerResponseList = customerCommandList.map(CustomerDto.Response::of);

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
