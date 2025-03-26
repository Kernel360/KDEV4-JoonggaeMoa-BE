package org.silsagusi.joonggaemoa.domain.message.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "messages")
@Getter
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private String scheduledAt;

	private LocalDateTime sendAt;

	private String content;

	public Message(Customer customer, LocalDateTime sendAt, String content) {
		this.customer = customer;
		this.scheduledAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.sendAt = sendAt;
		this.content = content;
	}
}
