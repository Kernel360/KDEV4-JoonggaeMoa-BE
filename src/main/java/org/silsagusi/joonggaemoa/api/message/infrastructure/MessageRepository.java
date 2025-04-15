package org.silsagusi.joonggaemoa.api.message.infrastructure;

import org.silsagusi.joonggaemoa.api.message.domain.Message;
import org.silsagusi.joonggaemoa.api.message.domain.SendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"customer", "customer.agent"})
    Page<Message> findAllByCustomer_Agent_Id(Long agentId, Pageable pageable);

    @EntityGraph(attributePaths = {"customer", "customer.agent"})
    Page<Message> findAllByCustomer_Agent_IdAndSendStatus(Long agentId, SendStatus sendStatus, Pageable pageable);

    Page<Message> findBySendStatusAndSendAtBetween(
        SendStatus status,
        LocalDateTime start,
        LocalDateTime end,
        Pageable pageable
    );
}
