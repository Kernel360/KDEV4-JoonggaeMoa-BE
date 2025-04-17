package org.silsagusi.api.contract.application.dto;

import java.time.LocalDate;

import org.silsagusi.core.domain.contract.info.ContractInfo;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContractDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "ContractRequestDto")
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

		public static Response of(ContractInfo info) {
			return Response.builder()
				.id(info.getId())
				.landlordName(info.getLandlordName())
				.tenantName(info.getTenantName())
				.createdAt(info.getCreatedAt())
				.expiredAt(info.getExpiredAt())
				.url(info.getUrl())
				.build();
		}

	}
}
