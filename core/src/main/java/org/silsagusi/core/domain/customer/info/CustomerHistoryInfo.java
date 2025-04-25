package org.silsagusi.core.domain.customer.info;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerHistoryInfo {
	private String id;
	private String type;
	private LocalDateTime date;

	// 상담용
	private String purpose;

	// 계약용
	private String role;
	private LocalDate startDate;
	private LocalDate endDate;

	// 문자용
	private String content;
	private String sendStatus;

	public LocalDateTime getSortDate() {
		if ("CONTRACT".equals(type)) {
			return endDate.atStartOfDay();
		}
		return date;
	}
}
