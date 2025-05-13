package org.silsagusi.api.customer.application.service;

import java.util.List;

import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.customer.application.dto.CreateCustomerRequest;
import org.silsagusi.api.customer.application.dto.CustomerHistoryResponse;
import org.silsagusi.api.customer.application.dto.CustomerInfiniteResponse;
import org.silsagusi.api.customer.application.dto.CustomerResponse;
import org.silsagusi.api.customer.application.dto.CustomerSummaryResponse;
import org.silsagusi.api.customer.application.dto.UpdateCustomerRequest;
import org.silsagusi.api.customer.application.mapper.CustomerMapper;
import org.silsagusi.api.customer.application.parser.CustomerExcelParser;
import org.silsagusi.api.customer.application.validator.CustomerValidator;
import org.silsagusi.api.customer.infrastructure.dataprovider.CustomerDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.command.UpdateCustomerCommand;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerHistoryInfo;
import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerDataProvider customerDataProvider;
	private final AgentDataProvider agentDataProvider;
	private final CustomerMapper customerMapper;
	private final CustomerExcelParser customerExcelParser;
	private final CustomerValidator customerValidator;

	@Transactional
	public void createCustomer(Long agentId, CreateCustomerRequest createCustomerRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		customerValidator.validateExist(agent, createCustomerRequest.getPhone(), createCustomerRequest.getEmail());
		Customer customer = customerMapper.toEntity(createCustomerRequest, agent);

		customerDataProvider.createCustomer(customer);
	}

	@Transactional
	public void bulkCreateCustomer(Long agentId, MultipartFile file) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		List<Customer> customers = customerExcelParser.toEntityList(agent, file);

		customerDataProvider.createCustomers(customers);
	}

	@Transactional
	public void deleteCustomer(Long agentId, Long customerId) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerValidator.validateAgentAccess(agentId, customer);

		customerDataProvider.deleteCustomer(customer);
	}

	@Transactional
	public void updateCustomer(Long agentId, Long customerId, UpdateCustomerRequest updateCustomerRequest) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerValidator.validateAgentAccess(agentId, customer);

		UpdateCustomerCommand updateCustomerCommand = updateCustomerRequest.toCommand(customer);

		customerDataProvider.updateCustomer(updateCustomerCommand);
	}

	@Transactional(readOnly = true)
	public CustomerHistoryResponse getCustomerById(Long agentId, Long customerId) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerValidator.validateAgentAccess(agentId, customer);
		List<CustomerHistoryInfo> customerHistoryInfos = customerDataProvider.getCustomerHistoryInfo(customer);
		return CustomerHistoryResponse.toResponse(customer, customerHistoryInfos);
	}

	@Transactional(readOnly = true)
	public Page<CustomerResponse> getAllCustomers(Long agentId, String keyword, Pageable pageable) {
		Page<Customer> customerPage = customerDataProvider.getAllByAgent(agentId, keyword, pageable);
		return customerPage.map(CustomerResponse::toResponse);
	}

	@Transactional(readOnly = true)
	public Slice<CustomerInfiniteResponse> getInfiniteCustomers(Long agentId, Long cursor, String keyword) {
		Slice<Customer> customers = customerDataProvider.getInfiniteCustomers(agentId, cursor, keyword);
		return customers.map(CustomerInfiniteResponse::toResponse);
	}

	@Transactional
	public String excelDownload() {
		return customerDataProvider.getExcelFormatFile();
	}

	@Transactional(readOnly = true)
	public CustomerSummaryResponse getCustomerSummary(Long agentId) {
		CustomerSummaryInfo customerSummaryInfo = customerDataProvider.getCustomerSummary(agentId);

		return CustomerSummaryResponse.toResponse(customerSummaryInfo);
	}
}
