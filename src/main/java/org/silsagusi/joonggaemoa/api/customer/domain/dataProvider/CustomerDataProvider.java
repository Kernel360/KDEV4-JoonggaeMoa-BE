package org.silsagusi.joonggaemoa.api.customer.domain.dataProvider;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;
import org.silsagusi.joonggaemoa.api.customer.domain.info.CustomerSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface CustomerDataProvider {

    //TODO
    Agent getAgent(Long agentId);
    
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
}
