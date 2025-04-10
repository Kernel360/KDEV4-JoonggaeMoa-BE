package org.silsagusi.joonggaemoa.domain.contract.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class ContractCommand {
    private String id;
    private Long landlordId;
    private Long tenantId;
    private String landlordName;
    private String tenantName;
    private String landlordPhone;
    private String tenantPhone;
    private String landlordEmail;
    private String tenantEmail;
    private LocalDate createdAt;
    private LocalDate expiredAt;
    private String url;

    public static ContractCommand of(Contract contract) {
        return ContractCommand.builder()
            .id(contract.getId())
            .landlordId(contract.getCustomerLandlord().getId())
            .tenantId(contract.getCustomerTenant().getId())
            .landlordName(contract.getCustomerLandlord().getName())
            .tenantName(contract.getCustomerTenant().getName())
            .landlordPhone(contract.getCustomerLandlord().getPhone())
            .tenantPhone(contract.getCustomerTenant().getPhone())
            .landlordEmail(contract.getCustomerLandlord().getEmail())
            .tenantEmail(contract.getCustomerTenant().getEmail())
            .createdAt(contract.getCreatedAt())
            .expiredAt(contract.getExpiredAt())
            .url(contract.getUrl())
            .build();
    }

}
