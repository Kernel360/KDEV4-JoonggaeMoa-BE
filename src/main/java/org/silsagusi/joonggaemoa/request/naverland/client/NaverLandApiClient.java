package org.silsagusi.joonggaemoa.request.naverland.client;

import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientArticleResponse;
import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientComplexResponse;
import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientRegionResponse;
import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientRegionRequestHeader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Component
public class NaverLandApiClient {

    WebClient webClient;
    ClientRegionRequestHeader clientRegionRequestHeader;

    public NaverLandApiClient() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        this.webClient = webClientBuilder.baseUrl("https://m.land.naver.com").build();
        this.webClient.mutate().defaultHeader(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/134.0.0.0 Safari/537.36"
        ).build();
    }

    public <T> T fetchList(String path, Class<T> responseType, String page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam(String.valueOf(clientRegionRequestHeader))
                        .queryParam("page", page)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public ClientArticleResponse fetchArticleList(String page) {
        return fetchList("/cluster/ajax/articleList", ClientArticleResponse.class, page);
    }

    public ClientComplexResponse fetchComplexList(String page) {
        return fetchList("/cluster/ajax/complexList", ClientComplexResponse.class, page);
    }

    public ClientRegionResponse fetchRegionList(String cortar) {
        return webClient.get()
                .uri("/api/regions/list?cortarNo=" +  cortar)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ClientRegionResponse.class)
                .block();
    }
}
