package org.silsagusi.batch.zigbang.infrastructure.dto;

public record ZigBangScrapeResult(Long scrapeStatusId, int lastPage, String errorMessage) {
}
