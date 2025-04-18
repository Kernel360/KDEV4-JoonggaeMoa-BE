package org.silsagusi.batch.naverland.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ch.hsr.geohash.GeoHash;
import org.silsagusi.batch.infrastructure.RegionRepository;
import org.silsagusi.batch.infrastructure.RegionScrapStatusRepository;
import org.silsagusi.batch.naverland.client.NaverLandApiClient;
import org.silsagusi.batch.naverland.service.dto.RegionResponse;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.RegionScrapStatus;
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

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		if (regionRepository.count() > 0) {
			log.info("지역 정보 이미 크롤링됨. 저장된 지역: {}개", regionRepository.count());
		}
		// 리셋할 수 있는 다른 방법은?

		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		queue.add("0000000000"); // 법정동코드 첫 요청값

		while (!queue.isEmpty()) {
			String code = queue.poll();
			if (!visited.add(code))
				continue;
			RegionResponse response = naverLandApiClient.fetchRegionList(code);

			if (response.getRegionList().isEmpty())
				continue;

			List<Region> regions = response.getRegionList().stream()
				.map(it -> new Region(
					it.getCortarNo(),
					it.getCenterLat(),
					it.getCenterLon(),
					it.getCortarName(),
					it.getCortarType()
				))
				.toList();

			regionRepository.saveAll(regions);
			regionScrapStatusRepository.saveAll(
				regions.stream()
					.map(region -> new RegionScrapStatus(region, 1, false, null))
					.toList()
			);

			regions.stream()
				.map(Region::getCortarNo)
				.forEach(queue::add);
			log.info("{}개 지역 저장 완료", regionRepository.count());
		}

		List<Region> dvsnRegions = regionRepository.findByCortarType("dvsn");
		dvsnRegions.forEach(region -> {
			String hash = GeoHash.withCharacterPrecision(
				region.getCenterLat(),
				region.getCenterLon(),
				12
			).toBase32();
			region.update(hash);
		});
		regionRepository.saveAll(dvsnRegions);
		log.info("직방 크롤링을 위한 지오해시값 저장 완료. RegionInitializer 종료");
	}
}