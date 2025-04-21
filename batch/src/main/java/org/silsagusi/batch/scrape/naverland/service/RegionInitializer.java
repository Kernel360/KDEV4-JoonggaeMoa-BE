package org.silsagusi.batch.scrape.naverland.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ch.hsr.geohash.GeoHash;
import org.silsagusi.batch.infrastructure.RegionRepository;
import org.silsagusi.batch.infrastructure.RegionScrapStatusRepository;
import org.silsagusi.batch.scrape.naverland.client.NaverLandApiClient;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandNewRegionResponse;
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

		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		queue.add("0000000000"); // 법정동코드 첫 요청값

		while (!queue.isEmpty()) {
			String code = queue.poll();
			if (!visited.add(code))
				continue;

			try {
				NaverLandNewRegionResponse response = naverLandApiClient.fetchRegionList(code);

				if (response == null || response.getRegionList() == null || response.getRegionList().isEmpty()) {
					log.info("No regions found for code: {}", code);
					continue;
				}

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
				log.info("{}개 지역 저장 완료, 현재 코드: {}", regionRepository.count(), code);
			} catch (Exception e) {
				log.error("Error processing region with code: {}, error: {}", code, e.getMessage(), e);
				// Continue with the next region even if this one fails
			}
		}

		try {
			List<Region> dvsnRegions = regionRepository.findByCortarType("dvsn");
			if (dvsnRegions == null || dvsnRegions.isEmpty()) {
				log.info("No division regions found to update geohash values");
			} else {
				log.info("Updating geohash values for {} division regions", dvsnRegions.size());

				for (Region region : dvsnRegions) {
					try {
						if (region.getCenterLat() == null || region.getCenterLon() == null) {
							log.warn("Region with ID {} has null latitude or longitude, skipping geohash update", region.getId());
							continue;
						}

						String hash = GeoHash.withCharacterPrecision(
							region.getCenterLat(),
							region.getCenterLon(),
							12
						).toBase32();
						region.updateGeohash(hash);
					} catch (Exception e) {
						log.error("Error updating geohash for region with ID {}: {}", region.getId(), e.getMessage(), e);
						// Continue with the next region even if this one fails
					}
				}

				regionRepository.saveAll(dvsnRegions);
				log.info("직방 크롤링을 위한 지오해시값 저장 완료");
			}
		} catch (Exception e) {
			log.error("Error updating geohash values: {}", e.getMessage(), e);
		}

		log.info("RegionInitializer 종료");
	}
}
