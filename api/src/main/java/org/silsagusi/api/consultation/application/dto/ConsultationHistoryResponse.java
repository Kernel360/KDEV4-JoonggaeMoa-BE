package org.silsagusi.api.consultation.application.dto;

import org.silsagusi.api.customer.application.dto.CustomerResponse;
import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultationHistoryResponse {
	private CustomerResponse customer;
	private Page<ConsultationResponse> consultations;
}
