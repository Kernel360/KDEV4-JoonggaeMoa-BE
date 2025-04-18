package org.silsagusi.api.customer.exception;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;

public class CustomerNotFoundException extends CustomException {

	public CustomerNotFoundException(Long customerId) {
		super(ErrorCode.NOT_FOUND_CUSTOMER, "ID : " + customerId);
	}
}
