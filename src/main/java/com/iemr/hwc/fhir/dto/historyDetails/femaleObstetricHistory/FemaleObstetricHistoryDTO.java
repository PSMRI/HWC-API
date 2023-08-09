package com.iemr.hwc.fhir.dto.historyDetails.femaleObstetricHistory;

import lombok.Data;

@Data
public class FemaleObstetricHistoryDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private String totalNoOfPreg;
    private String[] complicationPregList;
    private String[] femaleObstetricHistoryList;
}
