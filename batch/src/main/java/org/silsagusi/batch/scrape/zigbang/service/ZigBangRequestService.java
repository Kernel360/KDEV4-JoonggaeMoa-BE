package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.zigbang.client.ZigBangApiClient;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZigBangRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ArticleRepository articleRepository;

	@Async("scrapeExecutor")
	public void scrapZigBang(ScrapeStatus scrapeStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapeStatus.getRegion();
			log.info("직방 스크랩 시작: 지역 코드 {}", region.getCortarNo());
			// 읍면동 단위의 지역 범위만 스크랩 후 데이터 저장
			if (!"sec".equals(region.getCortarType())) {
				log.info("범위가 읍면동이 아니므로 스크랩을 건너뜁니다: 지역 코드 {}", region.getCortarNo());
				return;
			}

			String localCode = region.getCortarNo().substring(0, 8);
			ZigBangItemCatalogResponse response = zigbangApiClient.fetchItemCatalog(localCode);
			int count = response.getCount();
			log.info("직방 스크랩 완료: 지역 코드 {}, 처리된 매물 수 {}",
				scrapeStatus.getRegion().getCortarNo(), count);
		} catch (Exception e) {
			log.error("직방 스크랩 실패: 지역 코드 {}, 에러 메시지: {}",
				scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
			scrapeStatus.updateFailed(true, e.getMessage());
		} finally {
			try {
				articleDataProvider.saveArticles(articles);
				scrapeStatus.updateCompleted(true);
				int savedCount = articleRepository.saveAll(articles).size();
				if (savedCount != 0) {
					log.info("직방 매물 저장 완료: 지역 코드 {}, 저장된 매물 수 {}",
					scrapeStatus.getRegion().getCortarNo(), articles.size());
					Thread.sleep((long) (Math.random() * 10000));
				}
			} catch (Exception e) {
				log.error("직방 매물 저장 실패: 지역 코드 {}, 에러 메시지: {}",
					scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
			}
		}
	}
}
