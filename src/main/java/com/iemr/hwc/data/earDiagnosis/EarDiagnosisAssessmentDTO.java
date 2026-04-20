package com.iemr.hwc.data.earDiagnosis;

import lombok.Data;

@Data
public class EarDiagnosisAssessmentDTO {

    private Integer assessmentId;
    private String patientId;
    private Integer benVisitNo;
    private Boolean difficultyHearing;
    private String whisperTestResponse;
    private String hearingTestOutcome;
    private Boolean earPain;
    private Boolean earDischargePresent;
    private String foreignBodyInEar;
    private String earConditionType;
    private Boolean congenitalEarMalformation;
    private Integer userId;
    private Integer createdBY;
}