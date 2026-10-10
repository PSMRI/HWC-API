package com.iemr.hwc.data.opthalmic;

import lombok.Data;

import jakarta.persistence.*;


@Data
@Entity
@Table(name = "opthalmic_visit",schema = "db_iemr")
public class OphthalmicVisit {

    @Id
    @Column(name = "visitId", length = 36)
    private String visitId;

    @Column(name = "patientID", nullable = false, length = 36)
    private String patientID;

    @Column(name = "benVisitNo", nullable = false)
    private Integer benVisitNo;

    @Column(name = "isDiabetic")
    private Boolean isDiabetic;

    @Column(name = "screeningPerformed")
    private Boolean screeningPerformed;

    @Column(name = "visualAcuityChartUsed")
    private String visualAcuityChartUsed;

    @Column(name = "distVARight")
    private String distVARight;

    @Column(name = "distVALeft")
    private String distVALeft;

    @Column(name = "nearVA")
    private String nearVA;

    @Column(name = "caseIdConditions", columnDefinition = "TEXT")
    private String caseIdConditions;

    @Column(name = "cataractSymptoms")
    private Boolean cataractSymptoms;

    @Column(name = "glaucomaSymptoms")
    private Boolean glaucomaSymptoms;

    @Column(name = "diabeticRetinopathySymptoms")
    private Boolean diabeticRetinopathySymptoms;

    @Column(name = "presbyopiaSymptoms")
    private Boolean presbyopiaSymptoms;

    @Column(name = "trachomaStatus")
    private String trachomaStatus;

    @Column(name = "cornealDiseaseType")
    private String cornealDiseaseType;

    @Column(name = "vitaminADeficiency")
    private Boolean vitaminADeficiency;

    @Column(name = "injuryType", columnDefinition = "TEXT")
    private String injuryType;

    @Column(name = "foreignBodyRemoval")
    private String foreignBodyRemoval;

    @Column(name = "chemicalExposure")
    private Boolean chemicalExposure;

    @Column(name = "createdBy", nullable = false)
    private String createdBy;

    @Column(name = "createdDate", nullable = false)
    private Long createdDate;

    @Column(name = "updatedBy", nullable = false)
    private String updatedBy;

    @Column(name = "updatedDate", nullable = false)
    private Long updatedDate;

    @Column(name = "syncState", nullable = false)
    private Integer syncState = 0;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "beneficiary_id")
    private Long beneficiaryID;

    @Column(name = "beneficiary_reg_id")
    private Long beneficiaryRegID;
}