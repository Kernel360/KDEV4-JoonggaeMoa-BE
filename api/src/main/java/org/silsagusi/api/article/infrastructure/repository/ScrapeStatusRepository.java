package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScrapeStatusRepository extends JpaRepository<ScrapeStatus, Long> {
}
