package org.silsagusi.joonggaemoa.api.auth.userDetails;

import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
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
		Agent agent = agentRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
		return CustomUserDetails.create(agent);
	}
}
