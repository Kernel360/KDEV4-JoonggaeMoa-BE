package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexResponseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Component
public class NaverLandApiClient {

    private final WebClient webClient;

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

    public NaverLandApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://m.land.naver.com").build();
        this.webClient.mutate().defaultHeader(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/134.0.0.0 Safari/537.36"
        ).build();
    }

    public ArticleResponseDto fetchArticleList() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cluster/ajax/articleList")
                        .queryParam("rletTpCd", rletTpCd)
                        .queryParam("tradTpCd", tradTpCd)
                        .queryParam("z", 14)
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
                .bodyToMono(ArticleResponseDto.class)
                .block();
    }

    public ComplexResponseDto fetchComplexList() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cluster/ajax/complexList")
                        .queryParam("rletTpCd", rletTpCd)
                        .queryParam("tradTpCd", tradTpCd)
                        .queryParam("z", 14)
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
                .bodyToMono(ComplexResponseDto.class)
                .block();
    }
}
