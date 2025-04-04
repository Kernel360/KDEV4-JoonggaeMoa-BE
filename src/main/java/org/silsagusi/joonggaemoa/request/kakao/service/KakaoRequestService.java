package org.silsagusi.joonggaemoa.request.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.request.kakao.client.KakaoApiClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoRequestService {

    private final KakaoApiClient kakaoApiClient;

    public void getLatLon() throws InterruptedException {
        log.info("카카오 부동산 매물 정보 크롤링 시작");

        for (Integer i = 1; i <= 20; i++) {

            Thread.sleep((long) (Math.random() * 5000));
        }
    }
}
