package org.silsagusi.joonggaemoa.domain.customer.service;

import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.customer.service.command.CustomerCommand;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final AgentRepository agentRepository;
	private final AmazonS3 amazonS3;
	private static final String S3_BUCKET_NAME = "joonggaemoa";
	private static final String EXCEL_FORMAT_FILENAME = "format.xlsx";

	public void createCustomer(
		Long agentId,
		String name,
		LocalDate birthday,
		String phone,
		String email,
		String job,
		Boolean isVip,
		String memo,
		Boolean consent
	) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		Customer customer = new Customer(
			name,
			birthday,
			phone,
			email,
			job,
			isVip,
			memo,
			consent,
			agent
		);

		customerRepository.save(customer);

	}

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

	public void deleteCustomer(Long customerId) {
		customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

		customerRepository.deleteById(customerId);
	}

	public void updateCustomer(
		Long customerId,
		String name,
		LocalDate birthday,
		String phone,
		String email,
		String job,
		Boolean isVip,
		String memo,
		Boolean consent
	) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

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

	public CustomerCommand getCustomerById(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

		return CustomerCommand.of(customer);
	}

	public Page<CustomerCommand> getAllCustomers(Pageable pageable) {
		Page<Customer> customerPage = customerRepository.findAll(pageable);
		Page<CustomerCommand> customerCommandPage = customerPage.map(CustomerCommand::of);
		return customerCommandPage;
	}

	public Customer getCustomerByPhone(String phone) {
		return customerRepository.findByPhone(phone)
			.orElseGet(() -> null);
	}

	public String excelDownload() {
		return amazonS3.getUrl(S3_BUCKET_NAME, EXCEL_FORMAT_FILENAME).toString();
	}
}
