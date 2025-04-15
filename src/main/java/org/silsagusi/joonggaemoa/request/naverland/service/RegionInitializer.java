package org.silsagusi.joonggaemoa.request.naverland.service;

import java.util.List;

import org.silsagusi.joonggaemoa.api.article.entity.Region;
import org.silsagusi.joonggaemoa.api.article.entity.RegionScrapStatus;
import org.silsagusi.joonggaemoa.api.article.repository.RegionRepository;
import org.silsagusi.joonggaemoa.api.article.repository.RegionScrapStatusRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientRegionResponse;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegionInitializer {

	private final NaverLandApiClient naverLandApiClient;
	private final RegionRepository regionRepository;
	private final RegionScrapStatusRepository regionScrapStatusRepository;

	/* 법정동 코드
		법정동코드	법정동명
		1111000000	서울특별시 종로구
		1114000000	서울특별시 중구
		1117000000	서울특별시 용산구
		1120000000	서울특별시 성동구
		1121500000	서울특별시 광진구
		1123000000	서울특별시 동대문구
		1126000000	서울특별시 중랑구
		1129000000	서울특별시 성북구
		1130500000	서울특별시 강북구
		1132000000	서울특별시 도봉구
		1135000000	서울특별시 노원구
		1138000000	서울특별시 은평구
		1141000000	서울특별시 서대문구
		1144000000	서울특별시 마포구
		1147000000	서울특별시 양천구
		1150000000	서울특별시 강서구
		1153000000	서울특별시 구로구
		1154500000	서울특별시 금천구
		1156000000	서울특별시 영등포구
		1159000000	서울특별시 동작구
		1162000000	서울특별시 관악구
		1165000000	서울특별시 서초구
		1168000000	서울특별시 강남구
		1171000000	서울특별시 송파구
		1174000000	서울특별시 강동구
	 */
	public static final String[] cortars = {
		"1111000000",
		"1114000000",
		"1117000000",
		"1120000000",
		"1121500000",
		"1123000000",
		"1126000000",
		"1129000000",
		"1130500000",
		"1132000000",
		"1135000000",
		"1138000000",
		"1141000000",
		"1144000000",
		"1147000000",
		"1150000000",
		"1153000000",
		"1154500000",
		"1156000000",
		"1159000000",
		"1162000000",
		"1165000000",
		"1168000000",
		"1171000000",
		"1174000000",
	};

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		for (String cortarNo : cortars) {
			ClientRegionResponse response = naverLandApiClient.fetchRegionList(cortarNo);

			List<Region> regionList = response.getRegionList().stream()
				.map(it ->
					new Region(it.getCortarNo(), it.getCenterLat(), it.getCenterLon(), it.getCortarName(),
						it.getCortarType())
				)
				.toList();

			regionRepository.saveAll(regionList);

			List<RegionScrapStatus> statusList = regionList.stream()
				.map(region -> new RegionScrapStatus(region, 1, false, null))
				.toList();

			regionScrapStatusRepository.saveAll(statusList);
		}
		log.info("Region saved : {}", regionRepository.count());
	}
}
