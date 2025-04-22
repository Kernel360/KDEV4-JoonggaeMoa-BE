package org.silsagusi.api.customer.infrastructure;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.silsagusi.api.customer.exception.CustomerNotFoundException;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.command.UpdateCustomerCommand;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerDataProvider {

	private static final String S3_BUCKET_NAME = "joonggaemoa";
	private static final String EXCEL_FORMAT_FILENAME = "format.xlsx";
	private final CustomerRepository customerRepository;
	private final AmazonS3 amazonS3;

	public void createCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void createCustomers(List<Customer> customers) {
		customerRepository.saveAll(customers);
	}

	public void deleteCustomer(Customer customer) {
		customer.markAsDeleted();
		customerRepository.save(customer);
	}

	public Customer getCustomer(Long customerId) {
		return customerRepository.findByIdAndDeletedAtIsNull(customerId)
			.orElseThrow(() -> new CustomerNotFoundException(customerId));
	}

	public void updateCustomer(UpdateCustomerCommand updateCustomerCommand) {
		Customer customer = updateCustomerCommand.getCustomer();

		customer.updateCustomer(updateCustomerCommand.getName(), updateCustomerCommand.getBirthday(),
			updateCustomerCommand.getPhone(), updateCustomerCommand.getEmail(), updateCustomerCommand.getJob(),
			updateCustomerCommand.getIsVip(), updateCustomerCommand.getMemo(), updateCustomerCommand.getConsent(),
			updateCustomerCommand.getInterestProperty(), updateCustomerCommand.getInterestLocation(),
			updateCustomerCommand.getAssetStatus());

		customerRepository.save(customer);
	}

	public Page<Customer> getAllByAgent(Agent agent, Pageable pageable) {
		return customerRepository.findAllByAgentAndDeletedAtIsNull(agent, pageable);
	}

	public Customer getCustomerByPhone(String phone) {
		return customerRepository.findByPhoneAndDeletedAtIsNull(phone).orElse(null);
	}

	public String getExcelFormatFile() {
		return amazonS3.getUrl(S3_BUCKET_NAME, EXCEL_FORMAT_FILENAME).toString();
	}

	public CustomerSummaryInfo getCustomerSummary(Long agentId) {
		LocalDateTime now = LocalDateTime.now();

		LocalDate today = LocalDate.now();
		LocalDate thisWeekStart = today.with(DayOfWeek.MONDAY);
		LocalDateTime thisWeekStartTime = thisWeekStart.atStartOfDay();

		LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
		LocalDate lastWeekEnd = thisWeekStart.minusDays(1);
		LocalDateTime lastWeekStartTime = lastWeekStart.atStartOfDay();
		LocalDateTime lastWeekEndTime = lastWeekEnd.atTime(LocalTime.MAX);

		Long thisWeekCount = customerRepository.countByAgentIdAndCreatedAtBetweenAndDeletedAtIsNull(agentId,
			thisWeekStartTime, now);
		Long lastWeekCount = customerRepository.countByAgentIdAndCreatedAtBetweenAndDeletedAtIsNull(agentId,
			lastWeekStartTime, lastWeekEndTime);

		double changeRate;
		if (lastWeekCount == 0) {
			changeRate = thisWeekCount == 0 ? 0 : 100;
		} else {
			changeRate = ((double)(thisWeekCount - lastWeekCount) / lastWeekCount) * 100;
		}

		return CustomerSummaryInfo.builder().count(thisWeekCount).rate(changeRate).build();

	}

	public List<Customer> getCustomerListByIdList(List<Long> customerIdList) {
		return customerIdList.stream()

			.map(id -> customerRepository.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new CustomerNotFoundException(id))
			).toList();
	}
}