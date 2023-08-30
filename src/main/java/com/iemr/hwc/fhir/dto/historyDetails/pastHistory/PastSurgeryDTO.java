package com.iemr.hwc.fhir.dto.historyDetails.pastHistory;

import lombok.Data;

@Data
public class PastSurgeryDTO {
    private String surgeryID;
    private String surgeryType;
    private String otherSurgeryType;
    private String timePeriodAgo;
    private String timePeriodUnit;
}
