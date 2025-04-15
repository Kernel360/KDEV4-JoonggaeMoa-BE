package org.silsagusi.joonggaemoa.api.contract.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.api.contract.domain.Contract;

import java.time.LocalDate;

public class ContractDetailDto {

    @Getter
    @Builder
    public static class Response {
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

        public static Response of(Contract contract) {
            return Response.builder()
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
}
