package org.silsagusi.api.common.exception;

public class CustomerNotFoundException extends CustomException {

	public CustomerNotFoundException(Long customerId) {
		super(ErrorCode.NOT_FOUND_CUSTOMER, "ID : " + customerId);
	}
}
