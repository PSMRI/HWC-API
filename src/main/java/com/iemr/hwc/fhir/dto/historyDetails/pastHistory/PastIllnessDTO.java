package com.iemr.hwc.fhir.dto.historyDetails.pastHistory;

import lombok.Data;

@Data
public class PastIllnessDTO {
    private String illnessTypeID;
    private String illnessType;
    private String otherIllnessType;
    private String timePeriodAgo;
    private String timePeriodUnit;
}
