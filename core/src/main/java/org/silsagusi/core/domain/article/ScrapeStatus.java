package org.silsagusi.core.domain.article;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
		columnNames = {"region_id", "source"}))
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

	@Column(name = "completed")
	private Boolean completed;

	@Column(name = "last_scraped_at")
	private LocalDateTime lastScrapedAt;

	private Boolean failed;

	@Column(name = "error_message")
	private String errorMessage;

	private String source;

	public void updatePage(Integer page, LocalDateTime lastScrapedAt, String source) {
		this.lastScrapedPage = page;
		this.lastScrapedAt = lastScrapedAt;
		this.source = source;
	}

	public void completed() {
		this.completed = true;
	}

	public void failed(String errorMessage) {
		this.failed = true;
		this.errorMessage = errorMessage;
	}
}
