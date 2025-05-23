package org.silsagusi.api.customer.infrastructure.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.customer.entity.QCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

		if (StringUtils.isNotBlank(keyword)) {
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

	@Override
	public Page<Customer> findCustomerPage(Long agentId, String keyword, Pageable pageable) {
		QCustomer customer = QCustomer.customer;

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(customer.agent.id.eq(agentId));
		builder.and(customer.deletedAt.isNull());

		if (StringUtils.isNotBlank(keyword)) {
			builder.and(customer.name.containsIgnoreCase(keyword));
		}

		// 페이징된 데이터 조회
		List<Customer> content = queryFactory
			.selectFrom(customer)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(customer.createdAt.desc(), customer.id.desc())
			.fetch();

		// 전체 개수 조회
		Long total = queryFactory
			.select(customer.count())
			.from(customer)
			.where(builder)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}
}
