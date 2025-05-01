package org.silsagusi.api.inquiry.application.mapper;

import org.silsagusi.api.inquiry.application.dto.CreateInquiryRequest;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InquiryMapper {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Inquiry toEntity(CreateInquiryRequest inquiryCreateRequest) {
		return Inquiry.create(inquiryCreateRequest.getName(),
			bCryptPasswordEncoder.encode(inquiryCreateRequest.getPassword()),
			inquiryCreateRequest.getTitle(), inquiryCreateRequest.getContent());

	}
}
