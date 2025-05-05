package org.silsagusi.api.customer.application.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.api.customer.application.dto.CustomerExcelRequest;
import org.silsagusi.api.customer.application.mapper.CustomerMapper;
import org.silsagusi.api.customer.application.validator.CustomerValidator;
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
	private final CustomerValidator customerValidator;

	public List<Customer> toEntityList(Agent agent, MultipartFile file) {
		List<Customer> customers = new ArrayList<>();

		Workbook workbook = null;
		log.info("파일명: {}", file.getOriginalFilename());
		try {
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else if (file.getOriginalFilename().endsWith(".xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else {
				throw new CustomException(ErrorCode.INVALID_FILE);
			}
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INVALID_FILE);
		}

		int sheetsLength = workbook.getNumberOfSheets();

		for (int sheetIndex = 0; sheetIndex < sheetsLength; sheetIndex++) {
			Sheet workSheet = workbook.getSheetAt(sheetIndex);

			log.info("Row number: {}", workSheet.getLastRowNum());
			for (int i = 1; i <= workSheet.getLastRowNum(); i++) {
				log.info("row number: {}", i);
				try {
					Row row = workSheet.getRow(i);
					if (row == null) {
						continue;
					}

					CustomerExcelRequest customerExcelDto = CustomerExcelRequest.create(
						row.getCell(0).getStringCellValue(),
						row.getCell(1).getLocalDateTimeCellValue().toLocalDate(),
						row.getCell(2).getStringCellValue(),
						row.getCell(3).getStringCellValue(),
						row.getCell(4).getStringCellValue(),
						row.getCell(5).getBooleanCellValue(),
						getCellStringValueSafe(row.getCell(6)),
						row.getCell(7).getBooleanCellValue(),
						getCellStringValueSafe(row.getCell(8)),
						getCellStringValueSafe(row.getCell(9)),
						getCellStringValueSafe(row.getCell(10)),
						agent
					);

					Customer customer = customerMapper.toEntity(customerExcelDto);

					customerValidator.validateExist(agent, customer.getPhone(), customer.getEmail());

					customers.add(customer);

				} catch (CustomException e) {
					throw e;
				} catch (Exception e) {
					log.error("Error processing row " + (i) + ": " + e.getMessage());
					throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
				}
			}
		}

		return customers;
	}

	private String getCellStringValueSafe(Cell cell) {
		if (cell == null || cell.getCellType() == CellType.BLANK) {
			return null;
		}

		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			log.warn("Failed to get string value from cell, returning null: {}", e.getMessage());
			return null;
		}

	}

}
