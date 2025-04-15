package org.silsagusi.joonggaemoa.api.consultation.domain.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConsultationMonthInfo {
    private Long consultationAll;
    private Long consultationWaiting;
    private Long consultationConfirmed;
    private Long consultationCancelled;
    private Long consultationCompleted;
    private List<Integer> daysCount;
}
