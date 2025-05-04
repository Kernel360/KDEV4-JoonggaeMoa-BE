package org.silsagusi.api.customer.infrastructure.dataprovider;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.silsagusi.api.common.exception.CustomerNotFoundException;
import org.silsagusi.api.consultation.infrastructure.repository.ConsultationRepository;
import org.silsagusi.api.contract.infrastructure.repository.ContractRepository;
import org.silsagusi.api.customer.infrastructure.repository.CustomerRepository;
import org.silsagusi.api.message.infrastructure.repository.MessageRepository;
import org.silsagusi.api.survey.infrastructure.repository.AnswerRepository;
import org.silsagusi.core.domain.customer.command.UpdateCustomerCommand;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerHistoryInfo;
import org.silsagusi.core.domain.customer.info.CustomerSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerDataProvider {

	private static final String S3_BUCKET_NAME = "joonggaemoa";
	private static final String EXCEL_FORMAT_FILENAME = "format.xlsx";
	private final CustomerRepository customerRepository;
	private final ConsultationRepository consultationRepository;
	private final ContractRepository contractRepository;
	private final MessageRepository messageRepository;
	private final AnswerRepository answerRepository;
	private final AmazonS3 amazonS3;

	public void createCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void createCustomers(List<Customer> customers) {
		customerRepository.saveAll(customers);
	}

	public void deleteCustomer(Customer customer) {
		customer.markAsDeleted();
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
	}

	public Page<Customer> getAllByAgent(Long agentId, String keyword, Pageable pageable) {
		if (keyword == null || keyword.isEmpty()) {
			return customerRepository.findAllByAgent_IdAndDeletedAtIsNull(agentId, pageable);
		} else {
			return customerRepository.findAllByAgent_IdAndNameContainingIgnoreCaseAndDeletedAtIsNull(agentId, keyword,
				pageable);
		}
	}

	public Slice<Customer> getInfiniteCustomers(Long customerId, Long cursor, String keyword) {
		return customerRepository.findCustomerSlice(customerId, cursor, keyword);
	}

	public Customer getCustomerByPhone(String phone) {
		return customerRepository.findByPhoneAndDeletedAtIsNull(phone).orElse(null);
	}

	public String getExcelFormatFile() {
		return amazonS3.getUrl(S3_BUCKET_NAME, EXCEL_FORMAT_FILENAME).toString();
	}

	public List<CustomerHistoryInfo> getCustomerHistoryInfo(Customer customer) {
		List<CustomerHistoryInfo> customerHistoryInfos = new ArrayList<>();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startDate = now.minusMonths(3);
		LocalDateTime endDate = now.plusMonths(3);

		consultationRepository.findByCustomerAndDateBetweenAndDeletedAtIsNull(customer, startDate, endDate)
			.forEach(consultation -> {
				customerHistoryInfos.add(
					CustomerHistoryInfo.builder()
						.id(consultation.getId() + "")
						.type("CONSULTATION")
						.date(consultation.getDate())
						.purpose(consultation.getPurpose())
						.build()
				);
			});

		contractRepository.findContractsByCustomerAndDateRange(customer, startDate.toLocalDate(), endDate.toLocalDate())
			.forEach(contract -> {
				String role;
				if (contract.getCustomerLandlord().getId().equals(customer.getId())) {
					role = "LANDLORD";
				} else if (contract.getCustomerTenant().getId().equals(customer.getId())) {
					role = "TENANT";
				} else {
					throw new CustomerNotFoundException(customer.getId());
				}

				customerHistoryInfos.add(
					CustomerHistoryInfo.builder()
						.id(contract.getId())
						.type("CONTRACT")
						.role(role)
						.startDate(contract.getStartedAt())
						.endDate(contract.getExpiredAt())
						.build(
						));
			});

		messageRepository.findByCustomerAndSendAtBetweenAndDeletedAtIsNull(customer, startDate, endDate)
			.forEach(message -> {
				customerHistoryInfos.add(
					CustomerHistoryInfo.builder()
						.id(message.getId() + "")
						.type("MESSAGE")
						.date(message.getSendAt())
						.content(message.getContent())
						.sendStatus(message.getSendStatus() + "")
						.build()
				);
			});

		answerRepository.findAllByCustomerAndCreatedAtBetweenAndDeletedAtIsNull(
				customer, startDate, endDate)
			.forEach(answer -> {
				customerHistoryInfos.add(
					CustomerHistoryInfo.builder()
						.id(answer.getId() + "")
						.type("SURVEY")
						.date(answer.getCreatedAt())
						.build()
				);
			});

		customerHistoryInfos.sort(Comparator.comparing(CustomerHistoryInfo::getSortDate).reversed());
		return customerHistoryInfos;
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