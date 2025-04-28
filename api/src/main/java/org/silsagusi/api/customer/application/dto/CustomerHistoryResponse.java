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
	private CustomerDto.Response customer;
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
			.customer(CustomerDto.toResponse(customer))
			.history(
				customerHistoryInfos.stream()
					.map(History::create)
					.toList()
			).build();
	}
}
