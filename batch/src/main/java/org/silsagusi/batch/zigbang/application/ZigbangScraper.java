package org.silsagusi.batch.zigbang.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.application.ArticleMapper;
import org.silsagusi.batch.application.ArticleValidator;
import org.silsagusi.batch.application.ComplexMapper;
import org.silsagusi.batch.application.ComplexValidator;
import org.silsagusi.batch.infrastructure.dataprovider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataprovider.ComplexDataProvider;
import org.silsagusi.batch.zigbang.infrastructure.ZigBangApiClient;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZigbangScraper {

	private static final String BASE_32 = "0123456789bcdefghjkmnpqrstuvwxyz";

	private final ZigBangApiClient zigBangApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;
	private final ComplexMapper complexMapper;
	private final ArticleMapper articleMapper;
	private final ArticleValidator articleValidator;
	private final ComplexValidator complexValidator;

	public boolean scrapComplex(ScrapeStatus status) throws InterruptedException {
		Region region = status.getRegion();
		String geohashPrefix = region.getGeohash().substring(0, 4);

		for (char fifth : BASE_32.toCharArray()) {
			try {
				ZigBangDanjiResponse zigBangDanjiResponse = zigBangApiClient.fetchDanji(geohashPrefix + fifth);

				List<Complex> complexes = zigBangDanjiResponse.getFiltered().stream()
					.map(response -> complexMapper.toEntity(response, region))
					.filter(complexValidator::validateExist)
					.toList();

				complexDataProvider.saveComplexes(complexes);
			} catch (Exception e) {
				log.error(
					"스크래핑 실패: {} - {}. region_id: {}. 좌표값: {},{}. 에러 내용: {}",
					Platform.ZIGBANG, ScrapeTargetType.COMPLEX, region.getId(),
					region.getCenterLat(), region.getCenterLon(), e.getMessage(), e);
				Thread.sleep(600_000);
			} finally {
				status.updatePage(null, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
		}
		return true;
	}

	public boolean scrapArticle(ScrapeStatus status) throws InterruptedException {
		int offset = status.getLastScrapedPage();
		int limit = 20;
		Region region = status.getRegion();
		String localCode = region.getCortarNo().substring(0, 8);
		boolean hasMore = true;

		while (hasMore) {
			try {
				ZigBangItemCatalogResponse zigBangItemCatalogResponse = zigBangApiClient.fetchItemCatalog(localCode,
					offset, String.valueOf(limit));

				List<Article> articles = zigBangItemCatalogResponse.getList().stream()
					.map(response -> {
						Complex complex = complexDataProvider.getComplexByComplexCode(
							String.valueOf(response.getAreaDanjiId()));
						return articleMapper.toEntity(response, region, complex);
					})
					.filter(articleValidator::validateExist)
					.toList();

				articleDataProvider.saveArticles(articles);

				hasMore = zigBangItemCatalogResponse.getCount() >= offset;
			} catch (Exception e) {
				log.error(
					"스크래핑 실패: {} - {}. region_id: {} - {}페이지. 좌표값: {},{}. 에러 내용: {}",
					Platform.ZIGBANG, ScrapeTargetType.ARTICLE, region.getId(), offset,
					region.getCenterLat(), region.getCenterLon(), e.getMessage(), e);
				Thread.sleep(600_000);
			} finally {
				offset += limit;
				status.updatePage(offset, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
		}
		return true;
	}
}
