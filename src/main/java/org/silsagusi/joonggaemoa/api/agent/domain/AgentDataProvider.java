package org.silsagusi.joonggaemoa.api.agent.domain;

public interface AgentDataProvider {

	void createAgent(Agent agent);

	Agent getAgentById(Long agentId);

	Agent getAgentByNameAndPhone(String name, String phone);

	void updateAgent(Agent agent);

	void deleteRefreshTokenByAccessToken(String accessToken);

}
