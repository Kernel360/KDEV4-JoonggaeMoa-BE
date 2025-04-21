package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.ArticleDataProvider;
import org.silsagusi.batch.scrape.zigbang.client.ZigBangApiClient;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.ZigBangScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZigBangAptResponseService {
	private final ZigBangApiClient zigbangApiClient;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapExecutor")
	public void scrapItemCatalogs(ZigBangScrapeStatus scrapeStatus) {
		List<Article> articles = new ArrayList<>();

//		try{
//
//		} catch (Exception e) {
//			log.error("스크랩 실패 : {}", scrapeStatus.getRegion().getCortarNo(), e);
//			scrapeStatus.updateFailed(true, e.getMessage());
//		} finally {
//			ArticleDataProvider.saveArticles(articles);
//		}
	}

}
