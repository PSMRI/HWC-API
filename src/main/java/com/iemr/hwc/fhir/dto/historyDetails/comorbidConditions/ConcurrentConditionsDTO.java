package com.iemr.hwc.fhir.dto.historyDetails.comorbidConditions;

import lombok.Data;

@Data
public class ConcurrentConditionsDTO {

    private String comorbidConditionID;
    private String comorbidCondition;
    private String timePeriodAgo;
    private String timePeriodUnit;
    private String otherComorbidCondition;
    private Boolean isForHistory;

}
