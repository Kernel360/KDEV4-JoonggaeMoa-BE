package org.silsagusi.joonggaemoa.core.domain.message.entity;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.core.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.core.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "messages")
@Getter
public class Message extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private String content;

	@Enumerated(EnumType.STRING)
	private SendStatus sendStatus;

	private LocalDateTime sendAt;

	private String errorMessage;

	public Message(Customer customer, String content, LocalDateTime sendAt) {
		this.customer = customer;
		this.content = content;
		this.sendStatus = SendStatus.PENDING;
		this.sendAt = sendAt;
		this.errorMessage = null;
	}

	public void updateMessage(LocalDateTime sendAt, String content) {
		this.sendAt = sendAt;
		this.content = content;
	}

	public void updateSendStatus(SendStatus sendStatus) {
		this.sendStatus = sendStatus;
	}
}
