package org.silsagusi.api.exception;

public class AgentNotFoundException extends CustomException {
	public AgentNotFoundException(Long agentId) {
		super(ErrorCode.NOT_FOUND_USER, "ID : " + agentId);
	}

	public AgentNotFoundException(String name) {
		super(ErrorCode.NOT_FOUND_USER, "이름 : " + name);
	}
}
