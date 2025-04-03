package org.silsagusi.joonggaemoa.domain.message.repository;

import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findTop10ByCustomerAgent_IdOrderByIdDesc(Long agentId);

    List<Message> findTop10ByCustomerAgent_IdAndIdLessThanOrderByIdDesc(Long agentId, Long lastMessageId);

    Page<Message> findAll(Pageable pageable);
}
