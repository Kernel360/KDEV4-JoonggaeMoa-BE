package org.silsagusi.core.domain.notification.entity;

import org.silsagusi.core.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications", indexes = {
	@Index(name = "idx_notification_agent", columnList = "agent_id")
})
@Getter
@NoArgsConstructor
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "agent_id")
	private Long agentId;

	@Enumerated(EnumType.STRING)
	private NotificationType type;
	private String content;

	@Column(name = "is_read")
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
