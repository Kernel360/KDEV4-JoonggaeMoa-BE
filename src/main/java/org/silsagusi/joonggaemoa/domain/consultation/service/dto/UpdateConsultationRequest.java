package org.silsagusi.joonggaemoa.domain.consultation.service.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateConsultationRequest {
    private LocalDateTime date;
    private String purpose;
    private String interestProperty;
    private String interestLocation;
    private String contractType;
    private String assetStatus;
    private String memo;
    private String consultationStatus;

}
