package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Complex;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository.ComplexRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.NaverLandApiClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverLandCrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(NaverLandCrawlerService.class);
    private final NaverLandApiClient apiClient;
    private final ArticleRepository atclRepo;
    private final ComplexRepository cmplRepo;
    private ArticleResponseDto atclRspnsDto;
    private ComplexResponseDto cmplRspnsDto;
    private Article article;
    private Complex complex;

    // 스케줄러에서 호출하는 시작 메서드
    public void startCrawling() {
        logger.info("네이버 부동산 크롤링 시작");

        try {
            // 매물 정보 저장
            atclRspnsDto = apiClient.fetchArticleList();
            cmplRspnsDto = (ComplexResponseDto) apiClient.fetchComplexList();
            atclRepo.save(article);
            cmplRepo.save(complex);

            logger.info("매물 정보 저장 완료");

        } catch (Exception e) {
            logger.error("크롤링 오류", e);
        }
    }
}