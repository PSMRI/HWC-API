package com.iemr.hwc.fhir.dto.examinationDetails.gastroIntestinalExamination;

import lombok.Data;

@Data
public class GastroIntestinalExaminationDTO {
    private String inspection;
    private String palpation_AbdomenTexture;
    private String palpation_Liver;
    private String palpation_Spleen;
    private String palpation_Tenderness;
    private String palpation_LocationOfTenderness;
    private String percussion;
    private String auscultation;
    private String analRegion;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
