package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapeStatusRepository extends JpaRepository<ScrapeStatus, Long> {
}
