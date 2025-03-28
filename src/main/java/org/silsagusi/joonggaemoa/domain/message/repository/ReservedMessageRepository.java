package org.silsagusi.joonggaemoa.domain.message.repository;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservedMessageRepository extends JpaRepository<ReservedMessage, Long> {

	@Query("SELECT m FROM reserved_messages m "
		+ "WHERE m.sendAt >= :start AND m.sendAt <= :end")
	Slice<ReservedMessage> findPendingMessages(
		@Param("start") LocalDateTime start,
		@Param("end") LocalDateTime end,
		Pageable pageable
	);

}
