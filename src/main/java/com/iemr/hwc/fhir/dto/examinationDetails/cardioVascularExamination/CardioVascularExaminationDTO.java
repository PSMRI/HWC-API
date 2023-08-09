package com.iemr.hwc.fhir.dto.examinationDetails.cardioVascularExamination;

import lombok.Data;

@Data
public class CardioVascularExaminationDTO {
    private String jugularVenousPulse_JVP;
    private String apexbeatLocation;
    private String apexbeatType;
    private String firstHeartSound_S1;
    private String secondHeartSound_S2;
    private String additionalHeartSounds;
    private String murmurs;
    private String pericardialRub;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
