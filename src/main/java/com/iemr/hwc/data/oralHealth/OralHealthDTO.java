package com.iemr.hwc.data.oralHealth;

import lombok.Data;

@Data
public class OralHealthDTO {

    private Long id;
    private String patientID;
    private Integer benVisitNo;
    private Boolean toothDecayPresent;
    private String toothDecaySymptoms;
    private Boolean gumDiseasePresent;
    private String gumDiseaseSymptoms;
    private Boolean irregularTeethJaws;
    private Boolean abnormalGrowthUlcer;
    private Boolean cleftLipPalate;
    private Boolean dentalFluorosis;
    private String dentalEmergency;

    private Long createdDate;
    private String createdBy;
    private Long updatedDate;
    private String updatedBy;
    private Integer userId;
    private Long beneficiaryID;

    private Long beneficiaryRegID;
}