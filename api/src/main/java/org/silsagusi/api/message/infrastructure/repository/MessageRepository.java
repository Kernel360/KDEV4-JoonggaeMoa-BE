package org.silsagusi.api.message.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.message.entity.Message;
import org.silsagusi.core.domain.message.entity.SendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

	@EntityGraph(attributePaths = {"customer", "customer.agent"})
	Page<Message> findAllByCustomer_Agent_IdAndDeletedAtIsNull(Long agentId, Pageable pageable);

	@EntityGraph(attributePaths = {"customer", "customer.agent"})
	Page<Message> findAllByCustomer_Agent_IdAndSendStatusAndDeletedAtIsNull(Long agentId, SendStatus sendStatus,
		Pageable pageable);

	List<Message> findBySendStatusAndSendAtBetweenAndDeletedAtIsNull(
		SendStatus status,
		LocalDateTime start,
		LocalDateTime end
	);

	Optional<Message> findByIdAndDeletedAtIsNull(Long id);
}
