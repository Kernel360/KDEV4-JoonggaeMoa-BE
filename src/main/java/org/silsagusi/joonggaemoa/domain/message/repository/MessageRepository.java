package org.silsagusi.joonggaemoa.domain.message.repository;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findTop10ByCustomerAgent_IdOrderByIdDesc(Long agentId);

	List<Message> findTop10ByCustomerAgent_IdAndIdLessThanOrderByIdDesc(Long agentId, Long lastMessageId);

}
