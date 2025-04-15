package org.silsagusi.joonggaemoa.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.api.contract.domain.Contract;
import org.silsagusi.joonggaemoa.api.contract.infrastructure.ContractRepository;
import org.silsagusi.joonggaemoa.api.notify.application.NotificationService;
import org.silsagusi.joonggaemoa.api.notify.domain.NotificationType;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ContractExpireNotificationJobConfig {

    private final JobRepository jobRepository;
    private final ContractRepository contractRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final NotificationService notificationService;
    private static final String JOB_NAME = "contractExpireNotifyJob";
    private static final int CHUNK_SIZE = 10;

    @Bean
    public Job contractExpireJob(Step contractExpireStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(contractExpireStep)
            .build();
    }

    @Bean
    @JobScope
    public Step contractExpireStep() {
        return new StepBuilder(JOB_NAME + "Step", jobRepository)
            .<Contract, Void>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(contractReader())
            .processor(contractProcessor())
            .writer(items -> {
            })
            .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Contract> contractReader() {
        return new RepositoryItemReaderBuilder<Contract>()
            .name("contractReader")
            .repository(contractRepository)
            .methodName("findByExpiredAt")
            .arguments(List.of(LocalDate.now()))
            .pageSize(CHUNK_SIZE)
            .sorts(Map.of("id", Sort.Direction.ASC))
            .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Contract, Void> contractProcessor() {
        return contract -> {
            try {
                Long agentId = contract.getCustomerLandlord().getAgent().getId();
                String customerLandlordName = contract.getCustomerLandlord().getName();
                String customerTenantName = contract.getCustomerTenant().getName();
                String content = "고객 " + customerLandlordName + ", " + customerTenantName + "의 계약이 만료되었습니다.";

                notificationService.notify(agentId, NotificationType.CONTRACT, content);
                //writter 필요 없으므로 생략
                return null;
            } catch (Exception e) {
                log.error("계약 -> 알림 변환 중 에러 발생", e);
                return null;
            }
        };
    }

}
