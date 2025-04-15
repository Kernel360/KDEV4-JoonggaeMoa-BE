package org.silsagusi.joonggaemoa.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.customer.domain.Customer;
import org.silsagusi.joonggaemoa.api.message.domain.Message;
import org.silsagusi.joonggaemoa.api.message.domain.SendStatus;
import org.silsagusi.joonggaemoa.api.message.infrastructure.MessageRepository;
import org.silsagusi.joonggaemoa.api.message.infrastructure.SmsUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SmsBatchJogConfig {

    private static final String JOB_NAME = "messageSendingJob";
    private static final int CHUNK_SIZE = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final MessageRepository messageRepository;
    private final SmsUtil smsUtil;

    @Bean
    public Job createJob(Step messageSendStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(messageSendStep)
            .build();
    }

    @Bean
    @JobScope
    public Step messageSendStep() {
        return new StepBuilder(JOB_NAME + "Step", jobRepository)
            .<Message, Void>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(messageReader())
            .processor(messageProcessor())
            .writer(it -> {
            })
            .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Message> messageReader() {
        return new RepositoryItemReaderBuilder<Message>()
            .name("messageReader")
            .pageSize(CHUNK_SIZE)
            .methodName("findBySendStatusAndSendAtBetween")
            .arguments(SendStatus.PENDING, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30))
            .repository(messageRepository)
            .sorts(Map.of("id", Sort.Direction.DESC))
            .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Message, Void> messageProcessor() {
        return message -> {
            log.info("Processing reserved message: {}", message);
            if (message == null) {
                log.warn("Reserved message is null");
                return null;
            }

            try {
                if (message.getCustomer() == null || message.getContent() == null) {
                    log.error("Reserved message content is null");
                    return null;
                }

                Customer customer = message.getCustomer();
                Agent agent = customer.getAgent();
                String text = message.getContent();
                LocalDateTime sendAt = message.getSendAt();

                // smsUtil.sendMessage(agent.getPhone(), customer.getPhone(), text, sendAt);

                message.updateSendStatus(SendStatus.SENT);
                messageRepository.save(message);

                return null;
            } catch (Exception e) {
                log.error("Error processing reserved message", e);
                message.updateSendStatus(SendStatus.FAILED);
                messageRepository.save(message);

                return null;
            }
        };
    }
}
