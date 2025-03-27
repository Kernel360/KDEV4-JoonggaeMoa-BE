package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);
    // 네이버 부동산 클러스터 API 엔드포인트 (필요시 URL 수정)
    private static final String CLUSTER_URL = "https://m.land.naver.com/cluster/clusterList";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // 스케줄러에서 호출하는 시작 메서드
    public void startCrawling() {
        logger.info("네이버 부동산 크롤링 시작");
        try {
            String url = buildCrawlingUrl();
            logger.info("크롤링 URL: {}", url);
            String response = restTemplate.getForObject(url, String.class);
            processResponse(response);
        } catch (Exception e) {
            logger.error("크롤링 중 오류 발생", e);
        }
    }

    // API 호출을 위한 URL 생성. 조건들은 필요에 따라 외부 설정으로 분리 가능.
    private String buildCrawlingUrl() {
        return UriComponentsBuilder.fromHttpUrl(CLUSTER_URL)
                .queryParam("view", "atcl")
                .queryParam("cortarNo", "1168000000")  // 예시: 서울 강남구 법정동 코드
                .queryParam("rletTpCd", "APT")
                .queryParam("tradTpCd", "A1")
                .queryParam("z", "13")
                .queryParam("lat", "37.517408")
                .queryParam("lon", "127.047313")
                .queryParam("btm", "37.4546135")
                .queryParam("lft", "126.8825181")
                .queryParam("top", "37.5801497")
                .queryParam("rgt", "127.2121079")
                .queryParam("pCortarNo", "")
                .toUriString();
    }

    // API 응답 파싱 및 로그 출력. 추후 DB 저장 로직 추가 가능.
    private void processResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode articles = root.path("articles");  // articles 필드명은 실제 응답 구조에 맞게 수정할 것
            if (articles.isArray()) {
                articles.forEach(article -> {
                    logger.info("매물 정보: {}", article.toString());
                    // TODO: 매물 정보 도메인 객체 매핑 후 DB 저장 로직 추가
                });
            } else {
                logger.warn("응답 데이터 형식이 예상과 다름: articles 배열이 아님");
            }
        } catch (Exception e) {
            logger.error("응답 파싱 오류", e);
        }
    }
}