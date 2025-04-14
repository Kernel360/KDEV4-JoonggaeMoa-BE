package org.silsagusi.joonggaemoa.domain.contract.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ContractDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull
        private Long landlordId;

        @NotNull
        private Long tenantId;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate createdAt;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate expiredAt;
    }

    @Getter
    @Builder
    public static class Response {
        private String id;
        private String landlordName;
        private String tenantName;
        private LocalDate createdAt;
        private LocalDate expiredAt;
        private String url;

        public static Response of(Contract contract) {
            return Response.builder()
                .id(contract.getId())
                .landlordName(contract.getCustomerLandlord().getName())
                .tenantName(contract.getCustomerTenant().getName())
                .createdAt(contract.getCreatedAt())
                .expiredAt(contract.getExpiredAt())
                .url(contract.getUrl())
                .build();
        }

    }
}
