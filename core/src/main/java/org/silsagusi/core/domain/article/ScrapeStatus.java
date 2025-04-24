package org.silsagusi.core.domain.article;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "scrape_statuses")
@Table(uniqueConstraints =
	@UniqueConstraint(columnNames = { "region_id", "source" }))
@Getter
public class ScrapeStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scrape_status_id", nullable = false)
	private Long id;

	@OneToOne
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

	public ScrapeStatus(
		Region region, Integer lastScrapedPage, Boolean completed,
		LocalDateTime lastScrapedAt, String source) {
		this.region = region;
		this.lastScrapedPage = lastScrapedPage;
		this.completed = completed;
		this.lastScrapedAt = lastScrapedAt;
		this.source = source;
	}

	public void updatePage(Integer page, LocalDateTime lastScrapedAt, String source) {
		this.lastScrapedPage = page;
		this.lastScrapedAt = lastScrapedAt;
		this.source = source;
	}

	public void updateCompleted(Boolean completed) {
		this.completed = completed;
	}

	public void updateFailed(Boolean failed, String errorMessage) {
		this.failed = failed;
		this.errorMessage = errorMessage;
	}
}
