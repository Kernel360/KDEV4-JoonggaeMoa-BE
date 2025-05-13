package org.silsagusi.batch.infrastructure.dataprovider;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeStatusType;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapeStatusDataProvider {

	private final ScrapeStatusRepository scrapeStatusRepository;

	public ScrapeStatus getPendingScrapeStatus(Platform platform, ScrapeTargetType scrapeTargetType) {
		return scrapeStatusRepository.findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(
			platform, scrapeTargetType, ScrapeStatusType.PENDING);
	}

	public ScrapeStatus getFailedScrapeStatus(Platform platform, ScrapeTargetType scrapeTargetType) {
		return scrapeStatusRepository.findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(
			platform, scrapeTargetType, ScrapeStatusType.FAILED);
	}

	public void saveAll(Chunk<? extends ScrapeStatus> scrapeStatus) {
		scrapeStatusRepository.saveAll(scrapeStatus);
	}
}
