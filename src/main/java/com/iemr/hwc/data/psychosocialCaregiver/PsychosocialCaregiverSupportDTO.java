package com.iemr.hwc.data.psychosocialCaregiver;

import lombok.Data;

@Data
public class PsychosocialCaregiverSupportDTO {

    private Long id;
    private String patientId;
    private Integer benVisitNo;
    private Integer userId;
    private Long beneficiaryID;
    private Long beneficiaryRegID;
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
}
