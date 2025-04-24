package org.silsagusi.api.inquiry.application.validator;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InquiryValidator {

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public void validatePassword(Inquiry inquiry, final String password) {
		if (!bCryptPasswordEncoder.matches(password, inquiry.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_VALID);
		}
	}

}
