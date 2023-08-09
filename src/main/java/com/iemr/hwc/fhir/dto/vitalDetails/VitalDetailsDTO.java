package com.iemr.hwc.fhir.dto.vitalDetails;

import lombok.Data;

@Data
public class VitalDetailsDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String weight_Kg;
    private String height_cm;
    private String waistCircumference_cm;
    private String hipCircumference_cm;
    private String bMI;
    private String waistHipRatio;
    private String headCircumference_cm;
    private String midUpperArmCircumference_MUAC_cm;
    private String temperature;
    private String pulseRate;
    private String sPO2;
    private String systolicBP_1stReading;
    private String diastolicBP_1stReading;
    private String bloodGlucose_Fasting;
    private String bloodGlucose_Random;
    private String bloodGlucose_2hr_PP;
    private String respiratoryRate;
    private String rbsTestResult;
    private String rbsTestRemarks;
    private Boolean rbsCheckBox;
    private String hemoglobin;
    private Boolean frequentCoughChecked;
    private Boolean sputumChecked;
    private Boolean coughAtNightChecked;
    private Boolean wheezingChecked;
    private Boolean painInChestChecked;
    private Boolean shortnessOfBreathChecked;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
