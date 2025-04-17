package org.silsagusi.api.customer.application;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.silsagusi.api.survey.application.dto.AnswerDto;
import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.command.UpdateCustomerCommand;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CustomerMapper {

	public Customer toEntity(CustomerDto.Request customerRequestDto, Agent agent) {
		return Customer.create(
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

	public UpdateCustomerCommand toUpdateCustomerCommand(Customer customer, CustomerDto.Request customerRequestDto) {
		return UpdateCustomerCommand.builder()
			.customer(customer)
			.name(customerRequestDto.getName())
			.birthday(customerRequestDto.getBirthday())
			.phone(customerRequestDto.getPhone())
			.email(customerRequestDto.getEmail())
			.job(customerRequestDto.getJob())
			.isVip(customerRequestDto.getIsVip())
			.memo(customerRequestDto.getMemo())
			.consent(customerRequestDto.getConsent())
			.build();
	}

	public List<Customer> toEntityList(Agent agent, MultipartFile file) {
		List<Customer> customers = new ArrayList<>();
		try {
			//TODO: 엑셀 파일 타입 확인
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

			int sheetsLength = workbook.getNumberOfSheets();

			for (int sheetIndex = 0; sheetIndex < sheetsLength; sheetIndex++) {
				XSSFSheet workSheet = workbook.getSheetAt(sheetIndex);

				for (int i = 1; i < workSheet.getLastRowNum(); i++) {
					try {

						Row row = workSheet.getRow(i);
						if (row == null)
							continue;
						Customer customer = Customer.create(
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

						customers.add(customer);

					} catch (Exception innerException) {
						System.err.println("Error processiong row " + (i) + ": " + innerException.getMessage());
						throw innerException;
					}
				}
			}

		} catch (Exception e) {
			throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
		}
		return customers;
	}

	public Customer AnswerDtoToCustomer(AnswerDto.Request answerRequest, Agent agent) {
		return Customer.create(
			answerRequest.getName(),
			null,
			answerRequest.getPhone(),
			answerRequest.getEmail(),
			null,
			null,
			null,
			answerRequest.getConsent(),
			agent
		);
	}
}
