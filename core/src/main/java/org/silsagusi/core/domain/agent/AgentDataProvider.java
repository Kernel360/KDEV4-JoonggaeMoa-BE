package org.silsagusi.core.domain.agent;

public interface AgentDataProvider {

	void createAgent(Agent agent);

	void validateExist(Agent agent);

	Agent getAgentById(Long agentId);

	Agent getAgentByNameAndPhone(String name, String phone);

	void updateAgent(Agent agent);

	void deleteRefreshTokenByAccessToken(String accessToken);

}
