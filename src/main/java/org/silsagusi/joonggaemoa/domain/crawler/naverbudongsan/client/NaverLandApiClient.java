package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client;

import io.lettuce.core.dynamic.annotation.Value;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ArticleListResponseDto;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.ComplexListResponseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverLandApiClient {

    private final WebClient webClient;

    public NaverLandApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://m.land.naver.com").build();
        this.webClient.mutate().defaultHeader(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/134.0.0.0 Safari/537.36"
        ).build();
    }

    public ArticleListResponseDto fetchArticleList(
            @RequestParam(value = "rletTpCd", defaultValue = "${naverbudongsan.rletTpCd:APT}") String rletTpCd,
            @RequestParam(value = "tradTpCd", defaultValue = "${naverbudongsan.tradTpCd:A1}") String tradTpCd,
            @RequestParam(value = "lat", defaultValue = "${naverbudongsan.lat:37.517408}") String lat,
            @RequestParam(value = "lon", defaultValue = "${naverbudongsan.lon:127.047313}") String lon,
            @RequestParam(value = "btm", defaultValue = "${naverbudongsan.btm:37.4546135}") String btm,
            @RequestParam(value = "lft", defaultValue = "${naverbudongsan.lft:126.8825181}") String lft,
            @RequestParam(value = "top", defaultValue = "${naverbudongsan.top:37.5801497}") String top,
            @RequestParam(value = "rgt", defaultValue = "${naverbudongsan.rgt:127.2121079}") String rgt,
            @RequestParam(value = "cortarNo", defaultValue = "${naverbudongsan.cortarNo:1168000000}") String cortarNo
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
                .bodyToMono(ArticleListResponseDto.class)
                .block();
    }

    public ComplexListResponseDto fetchComplexList(
            @RequestParam(value = "rletTpCd", defaultValue = "${naverbudongsan.rletTpCd:APT}") String rletTpCd,
            @RequestParam(value = "tradTpCd", defaultValue = "${naverbudongsan.tradTpCd:A1}") String tradTpCd,
            @RequestParam(value = "lat", defaultValue = "${naverbudongsan.lat:37.517408}") String lat,
            @RequestParam(value = "lon", defaultValue = "${naverbudongsan.lon:127.047313}") String lon,
            @RequestParam(value = "btm", defaultValue = "${naverbudongsan.btm:37.4546135}") String btm,
            @RequestParam(value = "lft", defaultValue = "${naverbudongsan.lft:126.8825181}") String lft,
            @RequestParam(value = "top", defaultValue = "${naverbudongsan.top:37.5801497}") String top,
            @RequestParam(value = "rgt", defaultValue = "${naverbudongsan.rgt:127.2121079}") String rgt,
            @RequestParam(value = "cortarNo", defaultValue = "${naverbudongsan.cortarNo:1168000000}") String cortarNo
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
