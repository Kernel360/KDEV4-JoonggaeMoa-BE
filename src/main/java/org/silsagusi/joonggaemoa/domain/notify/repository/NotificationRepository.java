package org.silsagusi.joonggaemoa.domain.notify.repository;

import org.silsagusi.joonggaemoa.domain.notify.entity.Notification;
import org.silsagusi.joonggaemoa.domain.notify.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByAgentIdAndIsReadFalse(Long agentId);

    boolean existsByAgentIdAndTypeAndContent(Long agentId, NotificationType type, String content);

}
