package org.silsagusi.joonggaemoa.domain.notify.infrastructure;

import org.silsagusi.joonggaemoa.domain.notify.domain.Notification;
import org.silsagusi.joonggaemoa.domain.notify.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByAgentIdAndIsReadFalse(Long agentId);

	boolean existsByAgentIdAndTypeAndContent(Long agentId, NotificationType type, String content);

}
