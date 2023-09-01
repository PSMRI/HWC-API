package com.iemr.hwc.fhir.dto.examinationDetails.respiratorySystemExamination;

import lombok.Data;

@Data
public class RespiratoryExaminationSystemDTO {
    private String trachea;
    private String inspection;
    private String signsOfRespiratoryDistress;
    private String palpation;
    private String auscultation_Stridor;
    private String auscultation_BreathSounds;
    private String auscultation_Crepitations;
    private String auscultation_Wheezing;
    private String auscultation_PleuralRub;
    private String auscultation_ConductedSounds;
    private String percussion;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
