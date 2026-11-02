package com.iemr.hwc.data.throatDiagnosis;

import lombok.Data;

import java.util.List;

@Data
public class ThroatDiagnosisAssessmentDTO {

    private Integer assessmentId;
    private String patientID;
    private Integer benVisitNo;
    private Integer userId;
    private Long beneficiaryID;
    private Long beneficiaryRegID;
    private List<String> symptoms;
    private Boolean neckSwelling;
    private Boolean difficultySwallowing;
    private Boolean tonsillitis;
    private Boolean pharyngitis;
    private Boolean laryngitis;
    private Boolean sinusitis;
    private Boolean cleftLip;
    private Boolean cleftPalate;
}
