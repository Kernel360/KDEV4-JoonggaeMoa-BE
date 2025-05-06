package org.silsagusi.batch.zigbang.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.silsagusi.batch.application.ArticleValidator;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.batch.naverland.application.ArticleMapper;
import org.silsagusi.batch.naverland.application.ComplexMapper;
import org.silsagusi.batch.zigbang.infrastructure.ZigBangApiClient;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZigbangScraper {

	private final ZigBangApiClient zigBangApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;
	private final ComplexMapper complexMapper;
	private final ArticleMapper articleMapper;
	private final ArticleValidator articleValidator;

	public void scrapComplex(ScrapeStatus status) throws InterruptedException {
		Region region = status.getRegion();
		String geohash = region.getGeohash().substring(0, 5);

		try {
			ZigBangDanjiResponse zigBangDanjiResponse = zigBangApiClient.fetchDanji(geohash);

			List<Complex> complexes = zigBangDanjiResponse.getFiltered().stream()
				.map(response -> complexMapper.toEntity(response, region))
				.filter(articleValidator::validateExist)
				.toList();

			complexDataProvider.saveComplexes(complexes);

		} catch (Exception e) {
			log.warn(
				"Failed to scrape, platform: {}, target type: {}, region id: {}, latitude: {}, longitude: {}, message: {}",
				ScrapeStatus.Platform.ZIGBANG, ScrapeStatus.TargetType.COMPLEX, region.getId(),
				region.getCenterLat(), region.getCenterLon(), e.getMessage(), e);
			Thread.sleep(300_000);
		} finally {
			status.updatePage(null, LocalDateTime.now());
			Thread.sleep((long)(3000 + Math.random() * 4000));
		}
	}

	public void scrapArticle(ScrapeStatus status) throws InterruptedException {
		int offset = status.getLastScrapedPage();
		Region region = status.getRegion();
		String localCode = region.getCortarNo().substring(0, 8);
		boolean hasMore = true;

		// 임시
		ZigBangDanjiResponse danji = new ZigBangDanjiResponse();
		danji.setFiltered(new ArrayList<>());
		danji.getFiltered().add(new ZigBangDanjiResponse.ZigBangDanji());
		danji.getFiltered().get(0).setLat(region.getCenterLat());
		danji.getFiltered().get(0).setLng(region.getCenterLon());

		while (hasMore) {
			try {
				ZigBangItemCatalogResponse zigBangItemCatalogResponse = zigBangApiClient.fetchItemCatalog(localCode,
					offset);

				List<Article> articles = zigBangItemCatalogResponse.getList().stream()
					.map(response -> articleMapper.toEntity(response, danji, region, null, region.getCortarNo()))
					.filter(articleValidator::validateExist)
					.toList();

				articleDataProvider.saveArticles(articles);

				hasMore = zigBangItemCatalogResponse.getCount() >= offset;
				log.info("ZigbangArticleScraper scraped: {}", offset);
			} catch (Exception e) {
				log.warn(
					"Failed to scrape, platform: {}, target type: {}, region id: {}, latitude: {}, longitude: {}, page: {}, message: {}",
					ScrapeStatus.Platform.ZIGBANG, ScrapeStatus.TargetType.ARTICLE, region.getId(),
					region.getCenterLat(), region.getCenterLon(), offset, e.getMessage(), e);
				Thread.sleep(300_000);
			} finally {
				offset += 10;
				status.updatePage(offset, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
			break;
		}
	}
}
