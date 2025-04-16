package org.silsagusi.api.customer.application;

import org.silsagusi.api.agent.infrastructure.AgentDataProvider;
import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.silsagusi.api.customer.application.dto.CustomerSummaryResponse;
import org.silsagusi.api.customer.infrastructure.CustomerDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerDataProvider customerDataProvider;
	private final AgentDataProvider agentDataProvider;

	public void createCustomer(
		Long agentId,
		CustomerDto.Request customerRequestDto
	) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		customerDataProvider.validateExist(agent, customerRequestDto.getPhone(), customerRequestDto.getEmail());

		customerDataProvider.createCustomer(
			customerRequestDto.getName(),
			customerRequestDto.getBirthday(),
			customerRequestDto.getPhone(),
			customerRequestDto.getEmail(),
			customerRequestDto.getJob(),
			customerRequestDto.getIsVip(),
			customerRequestDto.getMemo(),
			customerRequestDto.getConsent(),
			agent
		);

	}

	public void bulkCreateCustomer(Long agentId, MultipartFile file) {
		customerDataProvider.bulkCreateCustomer(agentId, file);
	}

	public void deleteCustomer(Long agentId, Long customerId) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerDataProvider.validateAgentAccess(agentId, customer);

		customerDataProvider.deleteCustomer(customer);
	}

	public void updateCustomer(
		Long agentId, Long customerId, CustomerDto.Request customerRequestDto
	) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerDataProvider.validateAgentAccess(agentId, customer);

		customerDataProvider.updateCustomer(
			customer,
			customerRequestDto.getName(),
			customerRequestDto.getBirthday(),
			customerRequestDto.getPhone(),
			customerRequestDto.getEmail(),
			customerRequestDto.getJob(),
			customerRequestDto.getIsVip(),
			customerRequestDto.getMemo(),
			customerRequestDto.getConsent()
		);

	}

	public CustomerDto.Response getCustomerById(Long agentId, Long customerId) {
		Customer customer = customerDataProvider.getCustomer(customerId);
		customerDataProvider.validateAgentAccess(agentId, customer);
		return CustomerDto.Response.of(customer);
	}

	public Page<CustomerDto.Response> getAllCustomers(Long agentId, Pageable pageable) {
		Agent agent = agentDataProvider.getAgentById(agentId);

		Page<Customer> customerPage = customerDataProvider.getAllByAgent(agent, pageable);
		return customerPage.map(CustomerDto.Response::of);
	}

	public CustomerDto.Response getCustomerByPhone(String phone) {
		Customer customer = customerDataProvider.getCustomerByPhone(phone);
		return CustomerDto.Response.of(customer);
	}

	public String excelDownload() {
		return customerDataProvider.getExcelFormatFile();
	}

	public CustomerSummaryResponse getCustomerSummary(Long agentId) {

		CustomerSummaryInfo customerSummaryInfo = customerDataProvider.getCustomerSummary(agentId);

		return CustomerSummaryResponse.of(customerSummaryInfo);
	}
}
