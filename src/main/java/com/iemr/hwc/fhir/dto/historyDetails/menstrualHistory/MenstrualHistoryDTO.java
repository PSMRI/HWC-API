package com.iemr.hwc.fhir.dto.historyDetails.menstrualHistory;

import lombok.Data;

@Data
public class MenstrualHistoryDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private String menstrualCycleStatus;
    private String menstrualCycleStatusID;
    private String regularity;
    private String cycleLength;
    private String menstrualCyclelengthID;
    private String menstrualFlowDurationID;
    private String bloodFlowDuration;
    private String menstrualProblemList;

}
