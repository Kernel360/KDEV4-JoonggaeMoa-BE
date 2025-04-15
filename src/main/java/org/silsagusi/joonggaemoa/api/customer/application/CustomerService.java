package org.silsagusi.joonggaemoa.api.customer.application;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.silsagusi.joonggaemoa.api.customer.application.dto.CustomerDto;
import org.silsagusi.joonggaemoa.api.customer.application.dto.CustomerSummaryResponse;
import org.silsagusi.joonggaemoa.api.customer.domain.Customer;
import org.silsagusi.joonggaemoa.api.customer.infrastructure.CustomerRepository;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        CustomerDto.Request customerRequestDto
    ) {
        Agent agent = agentRepository.findById(agentId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (customerRepository.existsByAgentAndPhone(agent, customerRequestDto.getPhone())) {
            throw new CustomException(ErrorCode.CONFLICT_PHONE);
        }

        if (customerRepository.existsByAgentAndEmail(agent, customerRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.CONFLICT_EMAIL);
        }

        Customer customer = new Customer(
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

    public void deleteCustomer(Long agentId, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

        if (!customer.getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        customerRepository.delete(customer);
    }

    public void updateCustomer(
        Long agentId, Long customerId, CustomerDto.Request customerRequestDto
    ) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

        if (!customer.getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        customer.updateCustomer(
            (customerRequestDto.getName() == null || customerRequestDto.getName().isBlank()) ? customer.getName() :
                customerRequestDto.getName(),
            (customerRequestDto.getBirthday() == null) ? customer.getBirthday() : customerRequestDto.getBirthday(),
            (customerRequestDto.getPhone() == null || customerRequestDto.getPhone().isBlank()) ? customer.getPhone() :
                customerRequestDto.getPhone(),
            (customerRequestDto.getEmail() == null || customerRequestDto.getEmail().isBlank()) ? customer.getEmail() :
                customerRequestDto.getEmail(),
            (customerRequestDto.getJob() == null || customerRequestDto.getJob().isBlank()) ? customer.getJob() :
                customerRequestDto.getJob(),
            (customerRequestDto.getIsVip() == null) ? customer.getIsVip() : customerRequestDto.getIsVip(),
            (customerRequestDto.getMemo() == null || customerRequestDto.getMemo().isBlank()) ? customer.getMemo() :
                customerRequestDto.getMemo(),
            (customerRequestDto.getConsent() == null) ? customer.getConsent() : customerRequestDto.getConsent()
        );
        customerRepository.save(customer);
    }

    public CustomerDto.Response getCustomerById(Long agentId, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

        if (!customer.getAgent().getId().equals(agentId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        return CustomerDto.Response.of(customer);
    }

    public Page<CustomerDto.Response> getAllCustomers(Long agentId, Pageable pageable) {
        Agent agent = agentRepository.findById(agentId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Page<Customer> customerPage = customerRepository.findAllByAgent(agent, pageable);
        Page<CustomerDto.Response> customerCommandPage = customerPage.map(CustomerDto.Response::of);
        return customerCommandPage;
    }

    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone).orElse(null);
    }

    public String excelDownload() {
        return amazonS3.getUrl(S3_BUCKET_NAME, EXCEL_FORMAT_FILENAME).toString();
    }

    public CustomerSummaryResponse getCustomerSummary(Long agentId) {
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
            changeRate = ((double) (thisWeekCount - lastWeekCount) / lastWeekCount) * 100;
        }

        return new CustomerSummaryResponse(thisWeekCount, changeRate);

    }
}
