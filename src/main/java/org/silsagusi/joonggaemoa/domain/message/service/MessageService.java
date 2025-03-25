package org.silsagusi.joonggaemoa.domain.message.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.silsagusi.joonggaemoa.domain.message.repository.MessageRepository;
import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final ReservedMessageRepository reservedMessageRepository;

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	public Page<MessageCommand> getMessage(Long agentId, Long lastMessageId) {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("agentId").descending());

		return null;
	}

	public void testMessage() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", date)
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob("smsJob1"), jobParameters);
	}

}
