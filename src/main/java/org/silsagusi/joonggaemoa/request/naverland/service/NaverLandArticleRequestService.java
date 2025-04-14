package org.silsagusi.joonggaemoa.request.naverland.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.silsagusi.joonggaemoa.domain.article.dataProvider.ArticleDataProvider;
import org.silsagusi.joonggaemoa.domain.article.entity.Article;
import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.AddressResponse;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandArticleRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final KakaoAddressLookupService addressLookupService;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapExecutor")
	public void scrapArticles(RegionScrapStatus scrapStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapStatus.getRegion();
			if (region == null) {
				throw new IllegalArgumentException("Region이 null값임");
			}

			int page = scrapStatus.getLastScrapedPage();
			boolean hasMore = true;

			do {
				ClientArticleResponse response = naverLandApiClient.fetchArticleList(
					String.valueOf(page),
					region.getCenterLat().toString(),
					region.getCenterLon().toString(),
					region.getCortarNo()
				);

				if (response == null || response.getBody() == null) {
					log.warn("해당 지역에 대한 기사를 찾을 수 없습니다: {}", region.getCortarNo());
					hasMore = false;
					break;
				}

				List<Article> mappedArticles = mapToArticles(response.getBody(), region);
				if (mappedArticles != null && !mappedArticles.isEmpty()) {
					articles.addAll(mappedArticles);
				}

				page++;
				scrapStatus.updatePage(page, LocalDateTime.now());

				hasMore = response.isMore();

				// 3~7초 랜덤 딜레이
				Thread.sleep((long) (3000 + Math.random() * 4000));
			} while (hasMore && page <= 10);

			// 마지막 페이지까지 완료된 경우
			if (!hasMore) {
				scrapStatus.updateCompleted(true);
			}
		} catch (Exception e) {
			assert scrapStatus.getRegion() != null;
			log.error("스크랩 실패 : {}", scrapStatus.getRegion().getCortarNo());
			scrapStatus.updateFailed(true, e.getMessage());
		} finally {
			if (!articles.isEmpty()) {
				try {
					articleDataProvider.saveArticles(articles);
				} catch (Exception e) {
					log.error("기사 저장 실패", e);
				}
			}
		}
	}

	private List<Article> mapToArticles(List<ClientArticleResponse.Body> bodies, Region region) {
		if (bodies == null || region == null) {
			return new ArrayList<>();
		}

		return bodies.stream()
			.map(body -> {
				try {
					AddressResponse addr = addressLookupService.lookupAddress(body.getLat(), body.getLng());
					if (addr == null) {
						log.warn("주소 조회 실패: 위도={}, 경도={}", body.getLat(), body.getLng());
						return null;
					}

					return Article.createFrom(body, region, addr);
				} catch (Exception e) {
					log.error("기사 매핑 실패", e);
					return null;
				}
			})
			.filter(Objects::nonNull)
			.toList();
	}
}