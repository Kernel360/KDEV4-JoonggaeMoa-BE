package org.silsagusi.joonggaemoa.domain.consultation.service.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ConsultationMonthInformCommand {
    private Long consultationAll;
    private Long consultationWaiting;
    private Long consultationConfirmed;
    private Long consultationCancelled;
    private Long consultationCompleted;
    private List<Integer> daysCount;
}
