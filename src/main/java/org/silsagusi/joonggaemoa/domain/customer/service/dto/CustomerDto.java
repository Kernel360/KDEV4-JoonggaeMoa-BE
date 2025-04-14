package org.silsagusi.joonggaemoa.domain.customer.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CustomerDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotBlank
        private String name;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;

        @NotBlank
        @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 000-0000-0000")
        private String phone;

        @NotBlank
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        private String job;

        @NotNull
        private Boolean isVip;

        private String memo;

        @NotNull
        private Boolean consent;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        private String name;
        private LocalDate birthday;
        private String phone;
        private String email;
        private String job;
        private Boolean isVip;
        private String memo;
        private Boolean consent;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private LocalDate birthday;
        private String phone;
        private String email;
        private String job;
        private Boolean isVip;
        private String memo;
        private Boolean consent;

        public static Response of(Customer customer) {
            return Response.builder()
                .id(customer.getId())
                .name(customer.getName())
                .birthday(customer.getBirthday())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .job(customer.getJob())
                .isVip(customer.getIsVip())
                .memo(customer.getMemo())
                .consent(customer.getConsent())
                .build();
        }
    }
}
