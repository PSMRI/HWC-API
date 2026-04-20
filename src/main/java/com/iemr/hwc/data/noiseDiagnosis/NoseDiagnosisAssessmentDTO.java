package com.iemr.hwc.data.noiseDiagnosis;

import lombok.Data;

@Data
public class NoseDiagnosisAssessmentDTO {

    private Long id;
    private String patientId;
    private Integer benVisitNo;
    private Boolean difficultyBreathing;
    private Boolean openMouthBreathing;
    private Boolean noseBleed;
    private Integer systolicBp;
    private Integer diastolicBp;
    private String foreignBodyNose;
    private Boolean sinusitis;

    private Long createdDate;
    private String createdBy;
    private Long updatedDate;
    private String updatedBy;
}