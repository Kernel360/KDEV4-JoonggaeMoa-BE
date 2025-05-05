package org.silsagusi.api.common.auth.userDetails;

import org.silsagusi.api.agent.infrastructure.reposiroty.AgentRepository;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final AgentRepository agentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Agent agent = agentRepository.findByUsernameAndDeletedAtIsNull(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		if (!agent.getUsername().equals(username)) {
			throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
		}

		return CustomUserDetails.create(agent);
	}
}
