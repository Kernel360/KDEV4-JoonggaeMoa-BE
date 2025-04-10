package org.silsagusi.joonggaemoa.domain.contract.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;
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

        public static Response of(ContractCommand command) {
            return Response.builder()
                .id(command.getId())
                .landlordName(command.getLandlordName())
                .tenantName(command.getTenantName())
                .createdAt(command.getCreatedAt())
                .expiredAt(command.getExpiredAt())
                .url(command.getUrl())
                .build();
        }
    }
}
