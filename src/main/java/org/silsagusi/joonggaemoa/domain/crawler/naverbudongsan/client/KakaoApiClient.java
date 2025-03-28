package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto.AddressResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoApiClient {

    private final WebClient webClient;

    // kakao.api.key: application.yml에 설정된 Kakao REST API 키 (KakaoAK {API_KEY})
    @Autowired
    public KakaoApiClient(@Value("${kakao.api.key}") String kakaoApiKey) {
        // WebClient를 Kakao API의 기본 URL과 Authorization 헤더로 초기화한다.
        this.webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .build();
    }

    public KakaoApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * 주어진 좌표(경도, 위도)를 기반으로 주소 데이터를 조회한다.
     * @param longitude 경도 (x 좌표)
     * @param latitude 위도 (y 좌표)
     * @return AddressResponseDto: 카카오 API 응답 DTO
     */
    public AddressResponseDto getAddressByCoordinates(double longitude, double latitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/geo/coord2address.json")
                        .queryParam("x", longitude)
                        .queryParam("y", latitude)
                        .queryParam("input_coord", "WGS84") // 입력 좌표 체계
                        .build())
                .retrieve()
                .bodyToMono(AddressResponseDto.class)
                .block(); // 블로킹 방식으로 결과 반환
    }
}