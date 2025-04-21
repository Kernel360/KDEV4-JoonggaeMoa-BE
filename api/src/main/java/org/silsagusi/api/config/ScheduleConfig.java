package org.silsagusi.api.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.silsagusi.api.consultation.infrastructure.ConsultationRepository;
import org.silsagusi.api.contract.infrastructure.ContractRepository;
import org.silsagusi.api.message.infrastructure.repository.MessageRepository;
import org.silsagusi.api.notification.infrastructure.NotificationDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.message.entity.Message;
import org.silsagusi.core.domain.message.entity.SendStatus;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class ScheduleConfig {

	private static final String TIME_STAMP = "timeStamp";
	private static final String TIME_ZONE = "Asia/Seoul";

	private final MessageRepository messageRepository;
	private final ContractRepository contractRepository;
	private final ConsultationRepository consultationRepository;
	private final NotificationDataProvider notificationDataProvider;

	@Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul")
	@Transactional
	public void sendScheduledMessages() {
		log.info("Starting Send Message Scheduler");

		List<Message> messages = messageRepository.findBySendStatusAndSendAtBetweenAndDeletedAtIsNull(
			SendStatus.PENDING, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));

		for (Message message : messages) {
			log.info("Processing reserved message: {}", message);
			if (message == null) {
				log.warn("Reserved message is null");
				continue;
			}

			try {
				if (message.getCustomer() == null || message.getContent() == null) {
					log.error("Reserved message content is null");
					continue;
				}

				Customer customer = message.getCustomer();
				Agent agent = customer.getAgent();
				String text = message.getContent();
				LocalDateTime sendAt = message.getSendAt();

				// smsUtil.sendMessage(agent.getPhone(), customer.getPhone(), content, message.getSendAt());

				message.updateSendStatus(SendStatus.SENT);
				messageRepository.save(message);

				String content = String.format("고객 %s님에게 예약 문자 [%s]가 발송되었습니다", customer.getName(),
					message.getContent());

				notificationDataProvider.notify(agent.getId(), NotificationType.MESSAGE, content);
			} catch (Exception e) {
				log.error("Error processing reserved message", e);
				message.updateSendStatus(SendStatus.FAILED);
				messageRepository.save(message);
			}

		}
	}

	@Async
	@Scheduled(cron = "0 0/30 * * * *", zone = TIME_ZONE)
	@Transactional
	public void notifyTodayConsultations() {
		log.info("Checking today's consultation schedule start");

		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

		List<Consultation> consultations = consultationRepository.findByDateBetweenAndConsultationStatusAndDeletedAtIsNull(
			startOfDay, endOfDay, Consultation.ConsultationStatus.CONFIRMED);

		for (Consultation consultation : consultations) {
			try {
				Long agentId = consultation.getCustomer().getAgent().getId();
				String customerName = consultation.getCustomer().getName();
				String time = consultation.getDate().toLocalTime().toString();

				String content = String.format("고객 %s님의 상담이 오늘 %s 예정되어 있습니다.", customerName, time);

				notificationDataProvider.notify(agentId, NotificationType.CONSULTATION, content);
				log.info("상담 알림 전송: {}", content);

			} catch (Exception e) {
				log.error("상담 알림 전송 실패", e);
			}
		}

	}

	@Async
	@Scheduled(cron = "0 0 * * * *", zone = TIME_ZONE)
	@Transactional
	public void notifyContractExpiry() {
		log.info("Checking contract expiration schedule start");

		List<Contract> expiredContracts = contractRepository.findByExpiredAtAndDeletedAtIsNull(LocalDate.now());

		for (Contract contract : expiredContracts) {
			try {
				Long agentId = contract.getCustomerLandlord().getAgent().getId();
				String customerLandlordName = contract.getCustomerLandlord().getName();
				String customerTenantName = contract.getCustomerTenant().getName();
				String content = "고객 " + customerLandlordName + ", " + customerTenantName + "의 계약이 만료되었습니다.";

				// 알림 전송
				notificationDataProvider.notify(agentId, NotificationType.CONTRACT, content);
				log.info("계약 만료 알림 전송: {}", content);

			} catch (Exception e) {
				log.error("계약 -> 알림 변환 중 에러 발생", e);
			}
		}
	}

}