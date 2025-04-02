package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service;

import org.jetbrains.annotations.NotNull;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.AddressResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.command.ArticleCommand;
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
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.KakaoApiClient;

import java.util.List;

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
            log.info(String.valueOf(aResponse));

            while (aResponse.isMore()) {
                for (int i = 0; i < 20; i++) {

                    Article atcl = getArticle(i);
                    aRepo.save(atcl);
                }

                client.setPage(String.valueOf(Integer.parseInt(client.getPage()) + 1));
                aResponse = client.fetchArticleList();
                Thread.sleep((long) (Math.random() * 5000));
            }

            // 단지 정보 저장
            log.info("네이버 부동산 단지 정보 크롤링 시작");
            cResponse = client.fetchComplexList();
            log.info(String.valueOf(cResponse));

            while (cResponse.isMore()) {
                for (int i = 0; i < 20; i++) {

                    Complex cplx = getComplex(i);
                    cRepo.save(cplx);
                }

                client.setPage(String.valueOf(Integer.parseInt(client.getPage()) + 1));
                cResponse = client.fetchComplexList();
                Thread.sleep((long) (Math.random() * 5000));
            }

        } catch (NullPointerException e) {
            log.info(String.valueOf(aResponse));
            log.info(String.valueOf(cResponse));
            log.error("null값 오류");

        } catch (Exception e) {
            log.error("크롤링 오류", e);

        }
    }

    @NotNull
    private Article getArticle(int i) {
        ArticleResponseDto.ArticleList body = aResponse.getBody().get(i);

        return new Article(

                // name column
                body.getAtclNm(),

                // type column
                body.getRletTpNm(),

                // trade_type column
                body.getTradTpNm(),

                // price column
                body.getHanPrc(),

                // confirmed_at column
                body.getAtclCfmYmd(),

                // latitude column
                body.getLat(),

                // longitude column
                body.getLng()

        );
    }

    @NotNull
    private Complex getComplex(int i) {
        ComplexResponseDto.ComplexList result = cResponse.getResult().get(i);

        return new Complex(

                // name column
                result.getHscpNm(),

                // type column
                result.getHscpTypeNm(),

                // approved_at column
                result.getUseAprvYmd()
        );
    }

    public List<ArticleCommand> getAllArticles() {
        List<Article> articleList = aRepo.findAll();
        List<ArticleCommand> articleCommandList = articleList.stream()
                .map(it -> ArticleCommand.of(it)).toList();
        return articleCommandList;
    }
}
