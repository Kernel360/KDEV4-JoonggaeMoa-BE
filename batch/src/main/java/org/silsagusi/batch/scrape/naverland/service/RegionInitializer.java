package org.silsagusi.batch.scrape.naverland.service;

import java.util.*;

import ch.hsr.geohash.GeoHash;
import org.jetbrains.annotations.Nullable;
import org.silsagusi.batch.infrastructure.RegionRepository;
import org.silsagusi.batch.infrastructure.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.naverland.client.NaverLandApiClient;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandRegionResponse;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegionInitializer {

	private final RegionRepository regionRepository;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandApiClient naverLandApiClient;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void init() {

		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		queue.add("0000000000"); // 법정동코드 첫 요청값

		while (!queue.isEmpty()) {
			String bjdcode = queue.poll();
			if (!visited.add(bjdcode))
				continue;

			try {
				NaverLandRegionResponse response = naverLandApiClient.fetchRegionList(bjdcode);

				if (response == null || response.getRegionList() == null || response.getRegionList().isEmpty()) {
					continue;
				}

				List<Region> regions = response.getRegionList().stream()
					.map(it -> {
						Region region = new Region(
							it.getCortarNo(),
							it.getCenterLat(),
							it.getCenterLon(),
							it.getCortarName(),
							it.getCortarType()
						);
						region.updateGeohash(updateGeohashValue(it.getCenterLat(), it.getCenterLon()));
						return region;
					})
					.toList();

				regionRepository.saveAll(regions);

				scrapeStatusRepository.saveAll(
					regions.stream()
						.map(region -> new ScrapeStatus(
							region,
							1,
							false,
							null
						))
						.toList()
				);

				regions.stream()
					.map(Region::getCortarNo)
					.forEach(queue::add);

				Thread.sleep((long)(300 + Math.random() * 400));

			} catch (Exception e) {
				log.error("법정동 코드 {} 저장 에러 발생: {}", bjdcode, e.getMessage());
			}
		}
	}

	@Nullable
	private String updateGeohashValue(Double lat, Double lon) {
		try {
			return GeoHash.withCharacterPrecision(lat, lon, 12).toBase32();
		} catch (Exception e) {
			return null;
		}
	}
}
