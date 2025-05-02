package org.silsagusi.api.customer.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.info.CustomerHistoryInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerHistoryResponse {
	private Long id;
	private String name;
	private LocalDate birthday;
	private String phone;
	private String email;
	private String job;
	private Boolean isVip;
	private String memo;
	private Boolean consent;
	private String interestProperty;
	private String interestLocation;
	private String assetStatus;

	private List<History> history;

	@Getter
	@Builder
	public static class History {
		private String id;
		private HistoryType type;
		private LocalDateTime date;

		// 상담용
		private String purpose;

		// 계약용
		private LocalDate startDate;
		private LocalDate endDate;

		// 문자용
		private String content;
		private String sendStatus;

		public enum HistoryType {
			CONSULTATION, CONTRACT, MESSAGE, SURVEY
		}

		public static History create(CustomerHistoryInfo info) {
			return History.builder()
				.id(info.getId())
				.type(History.HistoryType.valueOf(info.getType()))
				.date(info.getDate())
				.purpose(info.getPurpose())
				.startDate(info.getStartDate())
				.endDate(info.getEndDate())
				.content(info.getContent())
				.sendStatus(info.getSendStatus())
				.build();
		}
	}

	public static CustomerHistoryResponse toResponse(Customer customer,
		List<CustomerHistoryInfo> customerHistoryInfos) {
		return CustomerHistoryResponse.builder()
			.id(customer.getId())
			.name(customer.getName())
			.birthday(customer.getBirthday())
			.phone(customer.getPhone())
			.email(customer.getEmail())
			.job(customer.getJob())
			.isVip(customer.getIsVip())
			.memo(customer.getMemo())
			.consent(customer.getConsent())
			.interestProperty(customer.getInterestProperty())
			.interestLocation(customer.getInterestLocation())
			.assetStatus(customer.getAssetStatus())
			.history(customerHistoryInfos.stream()
				.map(History::create)
				.toList())
			.build();
	}
}
