package org.silsagusi.joonggaemoa.api.notify.infrastructure;

import org.silsagusi.joonggaemoa.api.notify.domain.entity.Notification;
import org.silsagusi.joonggaemoa.api.notify.domain.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByAgentIdAndIsReadFalse(Long agentId);

    boolean existsByAgentIdAndTypeAndContent(Long agentId, NotificationType type, String content);

    List<Notification> findAllByAgentId(Long agentId);
}
