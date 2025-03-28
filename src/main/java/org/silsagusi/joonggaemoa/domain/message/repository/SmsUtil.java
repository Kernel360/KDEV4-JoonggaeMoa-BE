package org.silsagusi.joonggaemoa.domain.message.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmsUtil {

	@Value("${coolsms.api.key}")
	private String apiKey;

	@Value("${coolsms.api.secret}")
	private String apiSecretKey;

	private DefaultMessageService smsMessageService;

	@PostConstruct
	private void init() {
		smsMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
	}

	public void sendMessage(String from, String to, String text, LocalDateTime localDateTime) {
		Message message = new Message();
		message.setFrom(from);
		message.setTo(to);
		message.setText(text);

		try {
			log.info("Sending message: from - {}, to - {}, text - {}, sendAt - {}", from, to, text,
				localDateTime.toString());
			ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(localDateTime);
			Instant instant = localDateTime.toInstant(zoneOffset);

			MultipleDetailMessageSentResponse response = smsMessageService.send(message, instant);
			log.info("SMS response: {}", response);
		} catch (NurigoMessageNotReceivedException e) {
			log.error("message sending failed{}", e.getFailedMessageList());
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
