package org.silsagusi.joonggaemoa.domain.article.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageProxyService {

    public ResponseEntity<byte[]> getImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // 네이버 이미지 서버의 User-Agent 설정
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            // CORS 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "GET");
            headers.set("Access-Control-Allow-Headers", "Content-Type");
            
            // 이미지 타입에 따라 Content-Type 설정
            String contentType = connection.getContentType();
            headers.setContentType(MediaType.parseMediaType(contentType));
            
            try (InputStream inputStream = connection.getInputStream()) {
                byte[] imageBytes = inputStream.readAllBytes();
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("이미지 프록시 처리 중 오류 발생: {}", imageUrl, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 