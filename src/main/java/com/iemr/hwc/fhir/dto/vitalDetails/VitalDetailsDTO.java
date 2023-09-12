package com.iemr.hwc.fhir.dto.vitalDetails;

import lombok.Data;

@Data
public class VitalDetailsDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String visitCode;
    private String weight_Kg;
    private String height_cm;
    private String waistCircumference_cm;
    private String bMI;
    private String temperature;
    private String pulseRate;
    private String sPO2;
    private String systolicBP_1stReading;
    private String diastolicBP_1stReading;
    private String respiratoryRate;
    private String rbsTestResult;
    private String createdBy;
    private String modifiedBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
