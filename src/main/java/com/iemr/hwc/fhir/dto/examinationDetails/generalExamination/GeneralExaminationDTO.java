package com.iemr.hwc.fhir.dto.examinationDetails.generalExamination;

import lombok.Data;

@Data
public class GeneralExaminationDTO {
    private String consciousness;
    private String coherence;
    private String cooperation;
    private String comfortness;
    private String builtAndAppearance;
    private String gait;
    private String dangerSigns;
    private String[] typeOfDangerSigns;
    private String pallor;
    private String quickening;
    private String foetalMovements;
    private String jaundice;
    private String cyanosis;
    private String clubbing;
    private String lymphadenopathy;
    private String lymphnodesInvolved;
    private String typeOfLymphadenopathy;
    private String edema;
    private String extentOfEdema;
    private String edemaType;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
