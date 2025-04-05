package org.silsagusi.joonggaemoa.domain.notify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String type;
    private String content;
    private boolean read = false;

    public Notification(Long agentId, String type, String content) {
        this.agentId = agentId;
        this.type = type;
        this.content = content;
    }


}
