package org.silsagusi.api.contract.infrastructure.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.contract.entity.QContract;
import org.silsagusi.core.domain.customer.entity.QCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContractCustomRepositoryImpl implements ContractCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Contract> findContracts(Long agentId, String keyword, Pageable pageable) {
		QContract contract = QContract.contract;
		QCustomer landlord = QCustomer.customer;
		QCustomer tenant = new QCustomer("tenant");

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(contract.customerLandlord.agent.id.eq(agentId));
		builder.and(contract.deletedAt.isNull());

		if (StringUtils.isNotBlank(keyword)) {
			builder.and(
				contract.customerLandlord.name.containsIgnoreCase(keyword)
					.or(contract.customerTenant.name.containsIgnoreCase(keyword))
			);
		}

		List<Contract> content = queryFactory
			.selectFrom(contract)
			.leftJoin(contract.customerLandlord, landlord).fetchJoin()
			.leftJoin(contract.customerTenant, tenant).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(contract.createdAt.desc())
			.fetch();

		Long total = queryFactory
			.select(contract.count())
			.from(contract)
			.leftJoin(contract.customerLandlord, landlord)
			.leftJoin(contract.customerTenant, tenant)
			.where(builder)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}
}
