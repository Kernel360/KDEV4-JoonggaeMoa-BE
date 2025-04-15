package org.silsagusi.joonggaemoa.api.notify.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.global.BaseEntity;

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

    public Notification(Long agentId, NotificationType type, String content) {
        this.agentId = agentId;
        this.type = type;
        this.content = content;
    }

    public void markRead() {
        this.isRead = true;
    }
}
