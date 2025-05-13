package org.silsagusi.api.message.infrastructure.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.silsagusi.core.domain.customer.entity.QCustomer;
import org.silsagusi.core.domain.message.entity.Message;
import org.silsagusi.core.domain.message.entity.QMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Message> findMessagesByAgentAndCondition(Long agentId, String searchType, String keyword,
		Pageable pageable) {
		QMessage message = QMessage.message;
		QCustomer customer = QCustomer.customer;

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(message.customer.agent.id.eq(agentId));
		builder.and(message.deletedAt.isNull());

		if ("name".equalsIgnoreCase(searchType) && StringUtils.isNotBlank(keyword)) {
			builder.and(message.customer.name.containsIgnoreCase(keyword));
		} else if ("phone".equalsIgnoreCase(searchType) && StringUtils.isNotBlank(keyword)) {
			builder.and(message.customer.phone.containsIgnoreCase(keyword));
		}

		List<Message> content = queryFactory
			.selectFrom(message)
			.leftJoin(message.customer, customer).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(message.createdAt.desc())
			.fetch();

		Long total = queryFactory
			.select(message.count())
			.from(message)
			.leftJoin(message.customer, customer)
			.where(builder)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}
}
