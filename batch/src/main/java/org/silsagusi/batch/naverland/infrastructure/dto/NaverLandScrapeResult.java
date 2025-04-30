package org.silsagusi.batch.naverland.infrastructure.dto;

public record NaverLandScrapeResult(Long scrapeStatusId, int lastPage, String errorMessage) {
}