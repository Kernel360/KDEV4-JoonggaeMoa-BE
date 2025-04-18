package org.silsagusi.api.customer.infrastructure;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.silsagusi.api.agent.infrastructure.AgentRepository;
import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
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

	private final AgentRepository agentRepository;
	private final CustomerRepository customerRepository;

	private final AmazonS3 amazonS3;
	private static final String S3_BUCKET_NAME = "joonggaemoa";
	private static final String EXCEL_FORMAT_FILENAME = "format.xlsx";

	public void createCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void createCustomers(List<Customer> customers) {
		customerRepository.saveAll(customers);
	}

	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	public Customer getCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
		return customer;
	}

	public void updateCustomer(UpdateCustomerCommand updateCustomerCommand) {
		// TODO @DynamicUpdate
		Customer customer = updateCustomerCommand.getCustomer();
		String name = updateCustomerCommand.getName();
		LocalDate birthday = updateCustomerCommand.getBirthday();
		String phone = updateCustomerCommand.getPhone();
		String email = updateCustomerCommand.getEmail();
		String job = updateCustomerCommand.getJob();
		Boolean isVip = updateCustomerCommand.getIsVip();
		String memo = updateCustomerCommand.getMemo();
		Boolean consent = updateCustomerCommand.getConsent();

		customer.updateCustomer(
			(name == null || name.isBlank()) ? customer.getName() : name,
			(birthday == null) ? customer.getBirthday() : birthday,
			(phone == null || phone.isBlank()) ? customer.getPhone() : phone,
			(email == null || email.isBlank()) ? customer.getEmail() : email,
			(job == null || job.isBlank()) ? customer.getJob() : job,
			(isVip == null) ? customer.getIsVip() : isVip,
			(memo == null || memo.isBlank()) ? customer.getMemo() : memo,
			(consent == null) ? customer.getConsent() : consent
		);
		customerRepository.save(customer);
	}

	public Page<Customer> getAllByAgent(Agent agent, Pageable pageable) {
		return customerRepository.findAllByAgent(agent, pageable);
	}

	public Customer getCustomerByPhone(String phone) {
		return customerRepository.findByPhone(phone).orElse(null);
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

		Long thisWeekCount = customerRepository.countByAgentIdAndCreatedAtBetween(agentId, thisWeekStartTime, now);
		Long lastWeekCount = customerRepository.countByAgentIdAndCreatedAtBetween(agentId, lastWeekStartTime,
			lastWeekEndTime);

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
			.map(id -> customerRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))
			).toList();
	}
}
