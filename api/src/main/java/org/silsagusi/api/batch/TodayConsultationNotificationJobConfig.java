// package org.silsagusi.api.batch;
//
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.util.List;
// import java.util.Map;
//
// import org.silsagusi.joonggaemoa.api.consultation.infrastructure.ConsultationRepository;
// import org.silsagusi.joonggaemoa.api.notify.application.NotificationService;
// import org.silsagusi.core.domain.consultation.entity.Consultation;
// import org.silsagusi.core.domain.notification.entity.NotificationType;
// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.Step;
// import org.springframework.batch.core.configuration.annotation.JobScope;
// import org.springframework.batch.core.configuration.annotation.StepScope;
// import org.springframework.batch.core.job.builder.JobBuilder;
// import org.springframework.batch.core.repository.JobRepository;
// import org.springframework.batch.core.step.builder.StepBuilder;
// import org.springframework.batch.item.ItemProcessor;
// import org.springframework.batch.item.data.RepositoryItemReader;
// import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.domain.Sort;
// import org.springframework.transaction.PlatformTransactionManager;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Configuration
// @RequiredArgsConstructor
// public class TodayConsultationNotificationJobConfig {
//
// 	private final JobRepository jobRepository;
// 	private final PlatformTransactionManager transactionManager;
// 	private final ConsultationRepository consultationRepository;
// 	private final NotificationService notificationService;
//
// 	private static final String JOB_NAME = "todayConsultationNotifyJob";
// 	private static final int CHUNK_SIZE = 10;
//
// 	@Bean
// 	public Job todayConsultationNotifyJob(Step todayConsultationStep) {
// 		return new JobBuilder(JOB_NAME, jobRepository)
// 			.start(todayConsultationStep)
// 			.build();
// 	}
//
// 	@Bean
// 	@JobScope
// 	public Step todayConsultationStep() {
// 		return new StepBuilder(JOB_NAME + "Step", jobRepository)
// 			.<Consultation, Void>chunk(CHUNK_SIZE, transactionManager)
// 			.reader(todayConsultationReader())
// 			.processor(todayConsultationProcessor())
// 			.writer(items -> {
// 			}) // writer는 필요 없음
// 			.build();
// 	}
//
// 	@Bean
// 	@StepScope
// 	public RepositoryItemReader<Consultation> todayConsultationReader() {
// 		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
// 		LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
//
// 		return new RepositoryItemReaderBuilder<Consultation>()
// 			.name("todayConsultationReader")
// 			.repository(consultationRepository)
// 			.methodName("findByDateBetweenAndConsultationStatus")
// 			.arguments(List.of(startOfDay, endOfDay, Consultation.ConsultationStatus.CONFIRMED))
// 			.pageSize(CHUNK_SIZE)
// 			.sorts(Map.of("id", Sort.Direction.ASC))
// 			.build();
// 	}
//
// 	@Bean
// 	@StepScope
// 	public ItemProcessor<Consultation, Void> todayConsultationProcessor() {
// 		return consultation -> {
// 			try {
// 				Long agentId = consultation.getCustomer().getAgent().getId();
// 				String customerName = consultation.getCustomer().getName();
// 				String time = consultation.getDate().toLocalTime().toString();
//
// 				String content = String.format("고객 %s님의 상담이 오늘 %s 예정되어 있습니다.", customerName, time);
//
// 				notificationService.notify(agentId, NotificationType.CONSULTATION, content);
// 				return null;
// 			} catch (Exception e) {
// 				log.error("상담 알림 전송 실패", e);
// 				return null;
// 			}
// 		};
// 	}
// }
