package org.silsagusi.api.customer.application.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.customer.application.dto.CustomerExcelRequest;
import org.silsagusi.api.customer.application.mapper.CustomerMapper;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerExcelParser {

	private final CustomerMapper customerMapper;

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
						CustomerExcelRequest customerExcelRequest = CustomerExcelRequest.create(
							row.getCell(0).getStringCellValue(),
							row.getCell(1).getLocalDateTimeCellValue().toLocalDate(),
							row.getCell(2).getStringCellValue(),
							row.getCell(3).getStringCellValue(),
							row.getCell(4).getStringCellValue(),
							row.getCell(5).getBooleanCellValue(),
							row.getCell(6).getStringCellValue(),
							row.getCell(7).getBooleanCellValue(),
							null,
							null,
							null,
							agent
						);

						Customer customer = customerMapper.toEntity(customerExcelRequest);

						customers.add(customer);

					} catch (Exception innerException) {
						log.error("Error processing row " + (i) + ": " + innerException.getMessage());
						throw innerException;
						// TODO 안되는 것만 체크해서 알려주기
					}
				}
			}

		} catch (Exception e) {
			throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
		}
		return customers;
	}
}
