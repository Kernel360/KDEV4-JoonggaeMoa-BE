package org.silsagusi.api.consultation.infrastructure.repository;

import java.time.LocalDate;

import org.silsagusi.core.domain.consultation.entity.QConsultation;
import org.silsagusi.core.domain.consultation.enums.ConsultationStatus;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsultationCustomRepositoryImpl implements ConsultationCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public long countTodayConsultations(Long agentId, LocalDate today) {
		QConsultation c = QConsultation.consultation;

		Long count = queryFactory
			.select(c.count())
			.from(c)
			.where(
				c.date.between(today.atStartOfDay(), today.plusDays(1).atStartOfDay()),
				c.customer.agent.id.eq(agentId),
				c.consultationStatus.in(
					ConsultationStatus.WAITING,
					ConsultationStatus.CONFIRMED,
					ConsultationStatus.COMPLETED
				),
				c.deletedAt.isNull()
			)
			.fetchOne();

		return count != null ? count : 0;
	}

	@Override
	public long countTodayRemaining(Long agentId, LocalDate today) {
		QConsultation c = QConsultation.consultation;

		Long count = queryFactory
			.select(c.count())
			.from(c)
			.where(
				c.date.between(today.atStartOfDay(), today.plusDays(1).atStartOfDay()),
				c.customer.agent.id.eq(agentId),
				c.consultationStatus.in(ConsultationStatus.WAITING, ConsultationStatus.CONFIRMED),
				c.deletedAt.isNull()
			)
			.fetchOne();

		return count != null ? count : 0;
	}
}
