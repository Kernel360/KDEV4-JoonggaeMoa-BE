package org.silsagusi.core.domain.article;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeStatusType;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
	name = "scrape_statuses",
	uniqueConstraints = @UniqueConstraint(
		name = "scrape_status_unique_constraint",
		columnNames = {"region_id", "platform", "target_type"}))
@Getter
public class ScrapeStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scrape_status_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	@Column(name = "last_scraped_page")
	private Integer lastScrapedPage;

	@Column(name = "last_scraped_at")
	private LocalDateTime lastScrapedAt;

	@Enumerated(EnumType.STRING)
	private ScrapeStatusType status;

	@Enumerated(EnumType.STRING)
	private Platform platform;

	@Enumerated(EnumType.STRING)
	@Column(name = "target_type")
	private ScrapeTargetType targetType;

	public void updatePage(Integer page, LocalDateTime lastScrapedAt) {
		this.lastScrapedPage = page;
		this.lastScrapedAt = lastScrapedAt;
	}

	public ScrapeStatus initialize() {
		if (this.status.equals(ScrapeStatusType.IN_PROGRESS)) {
			return this;
		}
		this.status = ScrapeStatusType.PENDING;
		this.lastScrapedPage = 0;
		return this;
	}

	public void inProgress() {
		this.status = ScrapeStatusType.IN_PROGRESS;
	}

	public void completed() {
		this.status = ScrapeStatusType.COMPLETED;
	}

	public ScrapeStatus(Region region, Platform platform, ScrapeTargetType targetType) {
		this.region = region;
		this.platform = platform;
		this.targetType = targetType;
		this.status = ScrapeStatusType.PENDING;
	}
}
