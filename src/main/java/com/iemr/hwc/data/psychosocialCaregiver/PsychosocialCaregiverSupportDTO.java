package com.iemr.hwc.data.psychosocialCaregiver;

import lombok.Data;

@Data
public class PsychosocialCaregiverSupportDTO {

    private Long id;
    private String patientId;
    private Integer benVisitNo;
    private Boolean psychosocialCounsellingProvided;
    private Boolean caregiverCounsellingProvided;
    private Boolean caregiverDistressIdentified;
    private String counsellingRemarks;
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