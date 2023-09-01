package com.iemr.hwc.fhir.dto.historyDetails.personalHistory;

import lombok.Data;

@Data
public class TobaccoListDTO {
    private String tobaccoUseType;
    private String tobaccoUseTypeID;
    private String otherTobaccoUseType;
    private String number;
    private String numberperDay;
    private String numberperWeek;
    private String perDay;
    private String duration;
    private String durationUnit;

}
