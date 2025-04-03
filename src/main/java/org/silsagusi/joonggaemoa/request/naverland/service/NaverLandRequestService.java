package org.silsagusi.joonggaemoa.request.naverland.service;

import org.silsagusi.joonggaemoa.domain.article.repository.ComplexRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientComplexResponse;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.article.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientArticleResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class NaverLandRequestService {

    private final NaverLandApiClient client;
    private final ArticleRepository articleRepository;
    private final ComplexRepository complexRepository;
    private ClientArticleResponse clientArticleResponse;
    private ClientComplexResponse clientComplexResponse;

    public void scrap() throws InterruptedException {

        // 매물 정보
        do {
            log.info("네이버 부동산 매물 정보 크롤링 시작");
            log.info(String.valueOf(clientArticleResponse.getBody()));

            for (Integer i = 1; i <= 20; i++) {
                clientArticleResponse = client.fetchArticleList(i.toString());

                articleRepository.saveAll(clientArticleResponse.getBody());
            }
            Thread.sleep((long) (Math.random() * 5000));

        } while (clientArticleResponse.isMore());

        // 단지 정보
        do {
            log.info("네이버 부동산 단지 정보 크롤링 시작");
            log.info(String.valueOf(clientComplexResponse.getResult()));

            for (Integer i = 1; i <= 20; i++) {
                clientComplexResponse = client.fetchComplexList(i.toString());
                complexRepository.saveAll(clientComplexResponse.getResult());
            }
            Thread.sleep((long) (Math.random() * 5000));

        } while (clientComplexResponse.isMore());
    }
}
