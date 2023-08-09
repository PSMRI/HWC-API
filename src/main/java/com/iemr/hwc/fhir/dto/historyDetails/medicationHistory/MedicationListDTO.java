package com.iemr.hwc.fhir.dto.historyDetails.medicationHistory;

import lombok.Data;

@Data
public class MedicationListDTO {
    private String currentMedication;
    private String timePeriodAgo;
    private String timePeriodUnit;

}
