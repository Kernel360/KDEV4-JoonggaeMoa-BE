package org.silsagusi.api.customer.application.service;

import java.util.List;

import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.silsagusi.api.customer.application.dto.CustomerHistoryResponse;
import org.silsagusi.api.customer.application.dto.CustomerSummaryResponse;
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
	public void createCustomer(
		Long agentId,
		CustomerDto.Request customerRequestDto
	) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		customerValidator.validateExist(agent, customerRequestDto.getPhone(), customerRequestDto.getEmail());

		Customer customer = customerMapper.toEntity(customerRequestDto, agent);

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
	public void updateCustomer(Long agentId, Long customerId, CustomerDto.Request customerRequestDto) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerValidator.validateAgentAccess(agentId, customer);

		UpdateCustomerCommand updateCustomerCommand = CustomerDto.toCommand(customer, customerRequestDto);

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
	public Page<CustomerDto.Response> getAllCustomers(Long agentId, Pageable pageable) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		Page<Customer> customerPage = customerDataProvider.getAllByAgent(agent, pageable);
		return customerPage.map(CustomerDto::toResponse);
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
