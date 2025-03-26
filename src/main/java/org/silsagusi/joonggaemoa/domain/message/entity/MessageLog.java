package org.silsagusi.joonggaemoa.domain.message.entity;

import org.silsagusi.joonggaemoa.global.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "message_logs")
@Getter
public class MessageLog extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_log_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Message message;

	// TODO 수신자

	private String responseCode;

	private String errorMessage;

	private SendStatus sendStatus;

	private String smsMessageId;

	private String smsGroupId;

	enum SendStatus {
		READY, SENT, FAILED
	}
}
