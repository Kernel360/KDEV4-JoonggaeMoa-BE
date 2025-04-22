package org.silsagusi.api.contract.application.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

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
		private LocalDate startedAt;
		private LocalDate expiredAt;
		private String url;
	}
}
