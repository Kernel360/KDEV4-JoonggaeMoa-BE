package org.silsagusi.joonggaemoa.domain.consultation.contorller.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationMonthInformCommand;

import java.util.List;

@Getter
@Builder
public class ConsultationMonthInformResponse {
    private Long consultationAll;
    private Long consultationWaiting;
    private Long consultationConfirmed;
    private Long consultationCancelled;
    private Long consultationCompleted;
    private List<Integer> daysCount;

    public static ConsultationMonthInformResponse of(ConsultationMonthInformCommand consultationMonthInformCommand) {
        return ConsultationMonthInformResponse.builder()
            .consultationAll(consultationMonthInformCommand.getConsultationAll())
            .consultationWaiting(consultationMonthInformCommand.getConsultationWaiting())
            .consultationConfirmed(consultationMonthInformCommand.getConsultationConfirmed())
            .consultationCancelled(consultationMonthInformCommand.getConsultationCancelled())
            .consultationCompleted(consultationMonthInformCommand.getConsultationCompleted())
            .daysCount(consultationMonthInformCommand.getDaysCount())
            .build();
    }
}
