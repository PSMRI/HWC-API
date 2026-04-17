package com.iemr.hwc.data.throatDiagnosis;

import lombok.Data;

@Data
public class ThroatDiagnosisAssessmentDTO {

    private Long id;
    private String patientId;
    private Integer benVisitNo;
    private String symptoms;
    private Boolean neckSwelling;
    private Boolean difficultySwallowing;
    private Boolean tonsillitis;
    private Boolean pharyngitis;
    private Boolean laryngitis;
    private Boolean sinusitis;
    private Boolean cleftLip;
    private Boolean cleftPalate;

    private Long createdDate;
    private String createdBy;
    private Long updatedDate;
    private String updatedBy;
}