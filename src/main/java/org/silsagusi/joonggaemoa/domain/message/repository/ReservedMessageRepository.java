package org.silsagusi.joonggaemoa.domain.message.repository;

import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservedMessageRepository extends JpaRepository<ReservedMessage, Long> {

    @Query("SELECT m FROM reserved_messages m "
            + "WHERE m.sendAt >= :start AND m.sendAt <= :end")
    Slice<ReservedMessage> findPendingMessages(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    List<ReservedMessage> findTop10ByCustomerAgent_IdOrderByIdDesc(Long agentId);

    List<ReservedMessage> findTop10ByCustomerAgent_IdAndIdLessThanOrderByIdDesc(Long agentId, Long lastMessageId);

    Page<ReservedMessage> findAll(Pageable pageable);

}
