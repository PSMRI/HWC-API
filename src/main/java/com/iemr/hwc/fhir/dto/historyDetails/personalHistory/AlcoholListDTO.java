package com.iemr.hwc.fhir.dto.historyDetails.personalHistory;

import lombok.Data;

@Data
public class AlcoholListDTO {
    private String typeOfAlcohol;
    private String alcoholTypeID;
    private String otherAlcoholType;
    private String alcoholIntakeFrequency;
    private String avgAlcoholConsumption;
    private String duration;
    private String durationUnit;
}
