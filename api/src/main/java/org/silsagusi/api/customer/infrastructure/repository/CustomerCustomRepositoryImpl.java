package org.silsagusi.api.customer.infrastructure.repository;

import java.util.List;

import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.entity.QCustomer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerCustomRepositoryImpl implements CustomerCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Slice<Customer> findCustomerSlice(Long agentId, Long cursor, String keyword) {
		QCustomer customer = QCustomer.customer;
		int size = 10;

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(customer.agent.id.eq(agentId));
		builder.and(customer.deletedAt.isNull());

		if (keyword != null && !keyword.isBlank()) {
			builder.and(customer.name.containsIgnoreCase(keyword));
		}

		if (cursor != null) {
			builder.and(customer.id.lt(cursor));
		}

		List<Customer> content = queryFactory
			.selectFrom(customer)
			.where(builder)
			.orderBy(customer.createdAt.desc(), customer.id.desc())
			.limit(size + 1)  // 다음 페이지 여부 확인을 위해 +1
			.fetch();

		boolean hasNext = content.size() > size;

		if (hasNext) {
			content.remove(size);  // 다음 페이지 표시용 한 개 제거
		}

		return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);
	}
}
