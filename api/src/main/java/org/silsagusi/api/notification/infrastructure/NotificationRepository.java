package org.silsagusi.api.notification.infrastructure;

import java.util.List;

import org.silsagusi.core.domain.notification.entity.Notification;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByAgentIdAndIsReadFalse(Long agentId);

	boolean existsByAgentIdAndTypeAndContent(Long agentId, NotificationType type, String content);

	List<Notification> findAllByAgentId(Long agentId);
}
