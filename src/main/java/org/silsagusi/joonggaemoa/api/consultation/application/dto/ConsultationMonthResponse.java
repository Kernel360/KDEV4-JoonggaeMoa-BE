package org.silsagusi.joonggaemoa.api.consultation.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.core.domain.consultation.info.ConsultationMonthInfo;

import java.util.List;

@Getter
@Builder
public class ConsultationMonthResponse {
    private Long consultationAll;
    private Long consultationWaiting;
    private Long consultationConfirmed;
    private Long consultationCancelled;
    private Long consultationCompleted;
    private List<Integer> daysCount;

    public static ConsultationMonthResponse of(ConsultationMonthInfo info) {
        return ConsultationMonthResponse.builder()
            .consultationAll(info.getConsultationAll())
            .consultationWaiting(info.getConsultationWaiting())
            .consultationConfirmed(info.getConsultationConfirmed())
            .consultationCancelled(info.getConsultationCancelled())
            .consultationCompleted(info.getConsultationCompleted())
            .daysCount(info.getDaysCount())
            .build();
    }

}
