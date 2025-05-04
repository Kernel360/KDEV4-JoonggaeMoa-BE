package org.silsagusi.api.message.infrastructure.repository;

import org.silsagusi.core.domain.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageCustomRepository {
	Page<Message> findMessagesByAgentAndCondition(Long agentId, String searchType, String keyword, Pageable pageable);
}
