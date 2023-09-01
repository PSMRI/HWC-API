package com.iemr.hwc.fhir.dto.examinationDetails.headToToeExamination;

import lombok.Data;

@Data
public class HeadToToeExaminationDTO {
    private String headtoToeExam;
    private String head;
    private String eyes;
    private String ears;
    private String nose;
    private String oralCavity;
    private String throat;
    private String breastAndNipples;
    private String nipples;
    private String trunk;
    private String upperLimbs;
    private String lowerLimbs;
    private String skin;
    private String hair;
    private String nails;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
