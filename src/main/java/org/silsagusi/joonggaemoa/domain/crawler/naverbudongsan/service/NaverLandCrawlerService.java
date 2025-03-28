package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleListResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexListResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Complex;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository.ComplexRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.NaverLandApiClient;
import java.util.stream.Collectors;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;

@Service
@RequiredArgsConstructor
public class NaverLandCrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(NaverLandCrawlerService.class);
    private final ArticleRepository articleRepository;
    private final ComplexRepository complexRepository;
    private final NaverLandApiClient apiClient;

    @Value("${naverbudongsan.rletTpCd:APT}")
    private String rletTpCd;

    @Value("${naverbudongsan.tradTpCd:A1}")
    private String tradTpCd;

    @Value("${naverbudongsan.lat:37.517408}")
    private String lat;

    @Value("${naverbudongsan.lon:127.047313}")
    private String lon;

    @Value("${naverbudongsan.btm:37.4546135}")
    private String btm;

    @Value("${naverbudongsan.lft:126.8825181}")
    private String lft;

    @Value("${naverbudongsan.top:37.5801497}")
    private String top;

    @Value("${naverbudongsan.rgt:127.2121079}")
    private String rgt;

    @Value("${naverbudongsan.cortarNo:1168000000}")
    private String cortarNo;

    public Optional<Article> getArticle(Long atclNo) {
        return articleRepository.findById(atclNo);
    }

    public Optional<Complex> getComplex(Long atclNo) {
        return complexRepository.findById(atclNo);
    }

    // 스케줄러에서 호출하는 시작 메서드
    public void startCrawling() {
        logger.info("네이버 부동산 크롤링 시작");
        try {
            ArticleListResponseDto articleResponse = apiClient.fetchArticleList(
                    rletTpCd, tradTpCd, lat, lon, btm, lft, top, rgt, cortarNo);
            var articleEntities = articleResponse.getArticles().stream()
                    .map(dto -> ((ArticleDto) dto).toEntity())
                    .collect(Collectors.toList());
            articleRepository.saveAll(articleEntities);
            logger.info("Saved {} articles", articleEntities.size());

            ComplexListResponseDto complexResponse = apiClient.fetchComplexList(
                    rletTpCd, tradTpCd, lat, lon, btm, lft, top, rgt, cortarNo);
            var complexEntities = complexResponse.getComplexes().stream()
                    .map(dto -> ((ComplexDto) dto).toEntity())
                    .collect(Collectors.toList());
            complexRepository.saveAll(complexEntities);
            logger.info("Saved {} complexes", complexEntities.size());
        } catch (Exception e) {
            logger.error("크롤링 오류", e);
        }
    }
}