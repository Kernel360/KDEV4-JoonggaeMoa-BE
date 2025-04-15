package org.silsagusi.joonggaemoa.api.contract.domain.info;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.api.contract.domain.entity.Contract;

import java.time.LocalDate;


@Builder
@Getter
public class ContractInfo {

    private String id;
    private String landlordName;
    private String tenantName;
    private LocalDate createdAt;
    private LocalDate expiredAt;
    private String url;



    public static ContractInfo of(Contract contract) {
        return ContractInfo.builder()
            .id(contract.getId())
            .landlordName(contract.getCustomerLandlord().getName())
            .tenantName(contract.getCustomerTenant().getName())
            .createdAt(contract.getCreatedAt())
            .expiredAt(contract.getExpiredAt())
            .url(contract.getUrl())
            .build();
    }


}
