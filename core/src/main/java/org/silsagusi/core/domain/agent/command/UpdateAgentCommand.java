package org.silsagusi.core.domain.agent.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAgentCommand {
	private String username;
	private String name;
	private String phone;
	private String email;
	private String office;
	private String region;
	private String businessNo;
}
