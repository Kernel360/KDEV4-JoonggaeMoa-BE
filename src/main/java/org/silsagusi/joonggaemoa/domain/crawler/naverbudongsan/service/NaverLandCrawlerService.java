package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Complex;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository.ComplexRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.NaverLandApiClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandCrawlerService {

    private final NaverLandApiClient client;
    private final ArticleRepository aRepo;
    private final ComplexRepository cRepo;
    private ArticleResponseDto aResponse;
    private ComplexResponseDto cResponse;


    // 스케줄러에서 호출하는 시작 메서드
    public void startCrawling() {

        try {
            // 매물 정보 저장
            log.info("네이버 부동산 매물 정보 크롤링 시작");
            aResponse = client.fetchArticleList();
            log.info(aResponse.toString());
            while (aResponse.isMore()) {

                for (int i = 0; i < 20; i++) {
                    ArticleResponseDto.AL body = aResponse.getBody().get(i);

                    Article atcl = new Article(
                            body.getAtclNm(),
                            body.getCortarNo(),
                            body.getRletTpNm(),
                            body.getTradTpNm(),
                            body.getHanPrc(),
                            body.getAtclCfmYmd()
                    );
                    aRepo.save(atcl);
                }
                client.setPage(String.valueOf(Integer.parseInt(client.getPage()) + 1));
                aResponse = client.fetchArticleList();
                Thread.sleep((long) (5000 + Math.random() * 5000));
            }

            // 단지 정보 저장
            log.info("네이버 부동산 단지 정보 크롤링 시작");
            cResponse = client.fetchComplexList();
            System.out.println(cResponse);

            while (cResponse.isMore()) {

                for (int i = 0; i < 20; i++) {
                    ComplexResponseDto.CL result = cResponse.getResult().get(i);

                    Complex cplx = new Complex(
                            result.getHscpNm(),
                            result.getHscpTypeNm(),
                            result.getUseAprvYmd()
                    );
                    cRepo.save(cplx);
                }
                client.setPage(String.valueOf(Integer.parseInt(client.getPage()) + 1));
                cResponse = client.fetchComplexList();
                Thread.sleep((long) (5000 + Math.random() * 5000));
            }
        } catch (NullPointerException e) {
            System.out.println(aResponse.toString());
            System.out.println(cResponse.toString());
            log.error("null값 오류");
        } catch (Exception e) {
            log.error("크롤링 오류", e);
        }
    }
}