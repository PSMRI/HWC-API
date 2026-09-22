package com.iemr.hwc.data.noiseDiagnosis;

import lombok.Data;

@Data
public class NoseDiagnosisAssessmentDTO {

    private Long id;
    private String patientID;
    private Integer benVisitNo;
    private Integer userId;
    private Long beneficiaryID;
    private Long beneficiaryRegID;
    private Boolean difficultyBreathing;
    private Boolean openMouthBreathing;
    private Boolean noseBleed;
    private Integer systolicBp;
    private Integer diastolicBp;
    private String foreignBodyNose;
    private Boolean sinusitis;
}
