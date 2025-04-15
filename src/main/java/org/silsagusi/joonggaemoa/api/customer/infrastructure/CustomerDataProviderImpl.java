package org.silsagusi.joonggaemoa.api.customer.infrastructure;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.silsagusi.joonggaemoa.api.customer.domain.dataProvider.CustomerDataProvider;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;
import org.silsagusi.joonggaemoa.api.customer.domain.info.CustomerSummaryInfo;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerDataProviderImpl implements CustomerDataProvider {

	private final AgentRepository agentRepository;
	private final CustomerRepository customerRepository;

	private final AmazonS3 amazonS3;
	private static final String S3_BUCKET_NAME = "joonggaemoa";
	private static final String EXCEL_FORMAT_FILENAME = "format.xlsx";

	@Override
	public void createCustomer(String name, LocalDate birthday, String phone, String email, String job, Boolean isVip,
		String memo, Boolean consent, Agent agent) {

		Customer customer = new Customer(
			name, birthday, phone, email, job, isVip, memo, consent, agent
		);
		customerRepository.save(customer);
	}

	@Override
	public void validateExist(Agent agent, String phone, String email) {
		if (customerRepository.existsByAgentAndPhone(agent, phone)) {
			throw new CustomException(ErrorCode.CONFLICT_PHONE);
		}

		if (customerRepository.existsByAgentAndEmail(agent, email)) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}
	}

	@Override
	public void bulkCreateCustomer(Long agentId, MultipartFile file) {
		//TODO: 엑셀 파일 타입 확인
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

			int sheetsLength = workbook.getNumberOfSheets();
			Agent agent = agentRepository.findById(agentId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

			for (int sheetIndex = 0; sheetIndex < sheetsLength; sheetIndex++) {
				XSSFSheet workSheet = workbook.getSheetAt(sheetIndex);

				for (int i = 1; i < workSheet.getLastRowNum(); i++) {
					try {

						Row row = workSheet.getRow(i);
						if (row == null)
							continue;
						Customer customer = new Customer(
							row.getCell(0).getStringCellValue(),
							row.getCell(1).getLocalDateTimeCellValue().toLocalDate(),
							row.getCell(2).getStringCellValue(),
							row.getCell(3).getStringCellValue(),
							row.getCell(4).getStringCellValue(),
							row.getCell(5).getBooleanCellValue(),
							row.getCell(6).getStringCellValue(),
							row.getCell(7).getBooleanCellValue(),
							agent
						);

						customerRepository.save(customer);

					} catch (Exception innerException) {
						System.err.println("Error processiong row " + (i) + ": " + innerException.getMessage());
						throw innerException;
					}
				}
			}

		} catch (Exception e) {
			throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
		}
	}

	@Override
	public void validateAgentAccess(Long agentId, Customer customer) {
		if (!customer.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
		return customer;
	}

	@Override
	public void updateCustomer(
		Customer customer,
		String name, LocalDate birthday, String phone, String email,
		String job, Boolean isVip, String memo, Boolean consent
	) {
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

	@Override
	public Page<Customer> getAllByAgent(Agent agent, Pageable pageable) {
		return customerRepository.findAllByAgent(agent, pageable);
	}

	@Override
	public Customer getCustomerByPhone(String phone) {
		return customerRepository.findByPhone(phone).orElse(null);
	}

	@Override
	public String getExcelFormatFile() {
		return amazonS3.getUrl(S3_BUCKET_NAME, EXCEL_FORMAT_FILENAME).toString();
	}

	@Override
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

}
