package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleListResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexListResponseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverLandApiClient {

    private final WebClient webClient;

    public NaverLandApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://m.land.naver.com").build();
        this.webClient.mutate().defaultHeader(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36"
        ).build();
    }

    public ArticleListResponseDto fetchArticleList(
            String rletTpCd,
            String tradTpCd,
            String lat,
            String lon,
            String btm,
            String lft,
            String top,
            String rgt,
            String cortarNo
    ) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cluster/ajax/articleList")
                        .queryParam("rletTpCd", rletTpCd)
                        .queryParam("tradTpCd", tradTpCd)
                        .queryParam("z,", 14)
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("btm", btm)
                        .queryParam("lft", lft)
                        .queryParam("top", top)
                        .queryParam("rgt", rgt)
                        .queryParam("cortarNo", cortarNo)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // JSON을 DTO로 역직렬화
                .bodyToMono(ArticleListResponseDto.class)
                .block();
    }

    public ComplexListResponseDto fetchComplexList(
            String rletTpCd,
            String tradTpCd,
            String lat,
            String lon,
            String btm,
            String lft,
            String top,
            String rgt,
            String cortarNo
    ) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cluster/ajax/complexList")
                        .queryParam("rletTpCd", rletTpCd)
                        .queryParam("tradTpCd", tradTpCd)
                        .queryParam("z,", 14)
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("btm", btm)
                        .queryParam("lft", lft)
                        .queryParam("top", top)
                        .queryParam("rgt", rgt)
                        .queryParam("cortarNo", cortarNo)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // JSON을 DTO로 역직렬화
                .bodyToMono(ComplexListResponseDto.class)
                .block();
    }
}
