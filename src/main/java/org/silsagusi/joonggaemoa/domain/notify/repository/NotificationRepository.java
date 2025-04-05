package org.silsagusi.joonggaemoa.domain.notify.repository;

import org.silsagusi.joonggaemoa.domain.notify.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
