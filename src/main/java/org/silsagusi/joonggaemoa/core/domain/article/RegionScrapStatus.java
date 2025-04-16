package org.silsagusi.joonggaemoa.core.domain.article;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "region_scrap_statuses")
@Getter
public class RegionScrapStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "region_id", nullable = false, unique = true)
	private Region region;

	private Integer lastScrapedPage;

	private Boolean completed;

	private LocalDateTime lastScrapedAt;

	private Boolean failed;

	private String errorMessage;

	public RegionScrapStatus(Region region, Integer lastScrapedPage, Boolean completed, LocalDateTime lastScrapedAt) {
		this.region = region;
		this.lastScrapedPage = lastScrapedPage;
		this.completed = completed;
		this.lastScrapedAt = lastScrapedAt;
	}

	public void updatePage(Integer page, LocalDateTime lastScrapedAt) {
		this.lastScrapedPage = page;
		this.lastScrapedAt = lastScrapedAt;
	}

	public void updateCompleted(Boolean completed) {
		this.completed = completed;
	}

	public void updateFailed(Boolean failed, String errorMessage) {
		this.failed = failed;
		this.errorMessage = errorMessage;
	}
}
