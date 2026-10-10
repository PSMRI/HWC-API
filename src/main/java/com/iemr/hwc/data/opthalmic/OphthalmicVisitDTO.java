package com.iemr.hwc.data.opthalmic;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OphthalmicVisitDTO {

    private String visitId;
    private String patientID;
    private Integer benVisitNo;

    private Boolean isDiabetic;
    private Boolean screeningPerformed;

    private String visualAcuityChartUsed;
    private String distVARight;
    private String distVALeft;
    private String nearVA;

    private String caseIdConditions;

    private Boolean cataractSymptoms;
    private Boolean glaucomaSymptoms;
    private Boolean diabeticRetinopathySymptoms;
    private Boolean presbyopiaSymptoms;

    private String trachomaStatus;
    private String cornealDiseaseType;

    private Boolean vitaminADeficiency;

    private String injuryType;
    private String foreignBodyRemoval;
    private Boolean chemicalExposure;

    private String createdBy;
    private Long createdDate;
    private String updatedBy;
    private Long updatedDate;
    private Integer userId;
    private Long beneficiaryID;

    private Long beneficiaryRegID;

}