package com.iemr.hwc.data.elderlyHealth;

import lombok.Data;

@Data
public class ElderlyHealthAssessmentDTO {

    private Integer assessmentId;
    private String patientId;
    private Integer benVisitNo;
    private Integer userId;
    private Long beneficiaryID;
    private Long beneficiaryRegID;
    private Boolean geriatricComplaints;
    private Boolean multipleChronicConditions;
    private Boolean recentFalls;
    private Boolean difficultyWalkingBalance;
    private Boolean visualHearingDifficulty;
    private Boolean functionalDecline;
    private Boolean memoryLoss;
    private Boolean dementiaMemoryLoss;
    private Boolean dementiaDisorientation;
    private Boolean dementiaBehaviouralChanges;
    private Boolean dementiaSelfCareDecline;
    private String dementiaScreeningOutcome;
    private Boolean dementiaReferralRequired;
    private Boolean referralRequired;
    private String referralLevel;
    private String reasonForReferral;
    private Boolean followUpRequired;
    private String followUpDate;
    private String caseStatus;
    private String dateOfDeath;
    private String remarks;
}
