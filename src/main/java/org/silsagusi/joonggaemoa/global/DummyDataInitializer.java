package org.silsagusi.joonggaemoa.global;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;
import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
public class DummyDataInitializer {

	@Bean
	public ApplicationRunner insertDummyData(
		AgentRepository agentRepository,
		CustomerRepository customerRepository,
		ReservedMessageRepository reservedMessageRepository
	) {

		return args -> {
			// 1. Agent 3명 생성
			List<Agent> agents = new ArrayList<>();
			for (int i = 1; i <= 3; i++) {
				Agent agent = new Agent(
					"Agent" + i,
					"010-0000-000" + i,
					"agent" + i + "@example.com",
					"agentUsername" + i,
					"password" + i,
					"Office" + i,
					"Region" + i,
					"BusinessNo" + i
				);
				agents.add(agentRepository.save(agent));  // Agent 저장
			}

			// 2. Customer 15명 생성 (각 Agent에 5명씩 배정)
			List<Customer> customers = new ArrayList<>();
			IntStream.range(1, 16).forEach(i -> {
				Agent assignedAgent = agents.get((i - 1) / 5);  // 5명씩 나눠서 Agent 배정
				Customer customer = new Customer(
					"Customer" + i,
					LocalDate.of(1990, 1, i),
					"010-1234-56" + (i < 10 ? "0" + i : i),  // 010-1234-5601 형식
					"customer" + i + "@example.com",
					"Job" + i,
					i % 2 == 0,  // 짝수 번호는 VIP
					"Memo for Customer" + i,
					true,
					assignedAgent
				);
				customers.add(customerRepository.save(customer));  // Customer 저장
			});

			// 3. ReservedMessage 50개 생성 (1분 후부터 10초 간격으로 전송)
			LocalDateTime sendTime = LocalDateTime.now().plusSeconds(10);  // 현재 시간 기준 10초 뒤 시작
			for (int i = 0; i < 50; i++) {
				ReservedMessage reservedMessage = new ReservedMessage(
					customers.get(i % 15),  // 고객을 순환하면서 배정
					sendTime.plusSeconds(i * 60),  // 60초씩 증가
					"Reserved message content " + (i + 1)
				);
				reservedMessageRepository.save(reservedMessage);
			}

			log.info("더미 데이터 삽입 완료!");
		};
	}
}
