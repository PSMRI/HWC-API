package com.iemr.hwc.fhir.dto.examinationDetails.centralNervousSystemExamination;

import lombok.Data;

@Data
public class CentralNervousSystemExaminationDTO {
    private String handedness;
    private String cranialNervesExamination;
    private String motorSystem;
    private String sensorySystem;
    private String autonomicSystem;
    private String cerebellarSigns;
    private String signsOfMeningealIrritation;
    private String skull;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
