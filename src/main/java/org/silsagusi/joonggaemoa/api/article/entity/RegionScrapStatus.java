package org.silsagusi.joonggaemoa.api.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
