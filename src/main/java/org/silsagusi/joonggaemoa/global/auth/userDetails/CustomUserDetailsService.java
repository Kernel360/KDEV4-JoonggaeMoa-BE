package org.silsagusi.joonggaemoa.global.auth.userDetails;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.infrastructure.AgentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
