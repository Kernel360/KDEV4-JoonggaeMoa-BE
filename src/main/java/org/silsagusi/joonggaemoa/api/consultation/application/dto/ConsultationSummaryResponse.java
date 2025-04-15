package org.silsagusi.joonggaemoa.api.consultation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.api.consultation.domain.info.ConsultationSummaryInfo;

@Builder
@Getter
@AllArgsConstructor
public class ConsultationSummaryResponse {
    private Integer todayCount;
    private Integer remainingCount;

    public static ConsultationSummaryResponse of(ConsultationSummaryInfo info) {
        return ConsultationSummaryResponse.builder()
            .todayCount(info.getTodayCount())
            .remainingCount(info.getRemainingCount())
            .build();
    }
}
