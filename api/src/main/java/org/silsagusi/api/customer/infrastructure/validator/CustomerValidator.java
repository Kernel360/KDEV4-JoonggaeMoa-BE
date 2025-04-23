package org.silsagusi.api.customer.infrastructure.validator;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
import org.silsagusi.api.customer.infrastructure.repository.CustomerRepository;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerValidator {

	private final CustomerRepository customerRepository;

	public void validateExist(Agent agent, String phone, String email) {
		if (customerRepository.existsByAgentAndPhoneAndDeletedAtIsNull(agent, phone)) {
			throw new CustomException(ErrorCode.CONFLICT_PHONE);
		}

		if (customerRepository.existsByAgentAndEmailAndDeletedAtIsNull(agent, email)) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}
	}

	public void validateAgentAccess(Long agentId, Customer customer) {
		if (!customer.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}
