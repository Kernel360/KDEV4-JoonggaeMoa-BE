package org.silsagusi.joonggaemoa.api.message.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "message_templates")
@Getter
public class MessageTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_template_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agent;

    private String title;

    private String content;

    public MessageTemplate(Agent agent, String title, String content) {
        this.agent = agent;
        this.title = title;
        this.content = content;
    }

    public static MessageTemplate create(Agent agent, String title, String content) {
        return new MessageTemplate(agent, title, content);
    }

    public void updateMessageTemplate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
