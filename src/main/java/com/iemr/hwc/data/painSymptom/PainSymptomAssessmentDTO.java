package com.iemr.hwc.data.painSymptom;

import lombok.Data;

@Data
public class PainSymptomAssessmentDTO {

    private Long id;
    private String patientId;
    private Integer benVisitNo;
    private String painSeverity;
    private String painDuration;
    private Boolean symptomsPresent;
    private String otherSymptomsSeverity;
    private Boolean immediateReliefProvided;
    private Boolean persistentPainPresent;
    private Boolean painAssessmentEnabled;
    private String distressingSymptomsPresent;
    private Boolean bedriddenOrSeverelyDependent;
    private Boolean lifeLimitingIllnessKnown;
    private Boolean caregiverSupportRequired;
    private Boolean palliativeCareEligible;
    private Boolean referralRequired;
    private String referralLevel;
    private String reasonForReferral;
    private Boolean followUpRequired;
    private String followUpDate;
    private String caseStatus;
    private String dateOfDeath;
    private String remarks;

    private Long createdDate;
    private String createdBy;
    private Long updatedDate;
    private String updatedBy;
}