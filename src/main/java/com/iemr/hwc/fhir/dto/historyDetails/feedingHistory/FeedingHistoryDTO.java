package com.iemr.hwc.fhir.dto.historyDetails.feedingHistory;

import lombok.Data;

@Data
public class FeedingHistoryDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private String typeOfFeed;
    private String compFeedStartAge;
    private String noOfCompFeedPerDay;
    private String foodIntoleranceStatus;
    private String typeOfFoodIntolerances;
    private String otherFoodIntolerance;
}
