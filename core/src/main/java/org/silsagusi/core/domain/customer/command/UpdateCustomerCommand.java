package org.silsagusi.core.domain.customer.command;

import java.time.LocalDate;

import org.silsagusi.core.domain.customer.entity.Customer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCustomerCommand {
	private Customer customer;
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
}
