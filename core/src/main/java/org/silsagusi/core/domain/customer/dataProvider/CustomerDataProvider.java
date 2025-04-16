package org.silsagusi.core.domain.customer.dataProvider;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerDataProvider {

	Customer getCustomer(Long customerId);

	void createCustomer(String name, LocalDate birthday, String phone, String email,
		String job, Boolean isVip, String memo, Boolean consent, Agent agent);

	void validateExist(Agent agent, String phone, String email);

	void bulkCreateCustomer(Long agentId, MultipartFile file);

	void validateAgentAccess(Long agentId, Customer customer);

	void deleteCustomer(Customer customer);

	void updateCustomer(Customer customer,
		String name, LocalDate birthday, String phone, String email,
		String job, Boolean isVip, String memo, Boolean consent);

	Page<Customer> getAllByAgent(Agent agent, Pageable pageable);

	Customer getCustomerByPhone(String phone);

	String getExcelFormatFile();

	CustomerSummaryInfo getCustomerSummary(Long agentId);

	List<Customer> getCustomerListByIdList(List<Long> customerIdList);
}
