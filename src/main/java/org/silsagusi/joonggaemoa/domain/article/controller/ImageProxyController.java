package org.silsagusi.joonggaemoa.domain.article.controller;

import org.silsagusi.joonggaemoa.domain.article.service.ImageProxyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proxy")
@RequiredArgsConstructor
public class ImageProxyController {

    private final ImageProxyService imageProxyService;

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam String url) {
        return imageProxyService.getImage(url);
    }
} 