package org.silsagusi.core.domain.notification.entity;

import org.silsagusi.core.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private Long agentId;

	@Enumerated(EnumType.STRING)
	private NotificationType type;
	private String content;
	private Boolean isRead = false;

	private Notification(Long agentId, NotificationType type, String content) {
		this.agentId = agentId;
		this.type = type;
		this.content = content;
	}

	public static Notification create(Long agentId, NotificationType type, String content) {
		return new Notification(agentId, type, content);
	}

	public void markRead() {
		this.isRead = true;
	}
}
