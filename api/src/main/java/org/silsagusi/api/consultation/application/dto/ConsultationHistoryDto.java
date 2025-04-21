package org.silsagusi.api.consultation.application.dto;

import org.silsagusi.api.customer.application.dto.CustomerDto;
import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultationHistoryDto {
	private CustomerDto.Response customer;
	private Page<ConsultationDto.Response> consultations;
}
