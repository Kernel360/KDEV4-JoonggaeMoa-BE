package org.silsagusi.core.domain.consultation.info;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConsultationSummaryInfo {
    private Integer todayCount;
    private Integer remainingCount;
}

