package org.silsagusi.joonggaemoa.domain.customer.service.command;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCommand {
	private Long id;
	private String name;
	private LocalDate birthday;
	private String phone;
	private String email;
	private String job;
	private Boolean isVip;
	private String memo;
	private Boolean consent;

	public static CustomerCommand of(Customer customer) {
		return CustomerCommand.builder()
			.id(customer.getId())
			.name(customer.getName())
			.birthday(customer.getBirthday())
			.phone(customer.getPhone())
			.email(customer.getEmail())
			.job(customer.getJob())
			.isVip(customer.getIsVip())
			.memo(customer.getMemo())
			.consent(customer.getConsent())
			.build();
	}

}
