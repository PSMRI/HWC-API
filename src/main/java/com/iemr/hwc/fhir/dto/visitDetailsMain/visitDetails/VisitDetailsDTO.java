package com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails;

import lombok.Data;

@Data
public class VisitDetailsDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String visitNo;
    private String visitReason;
    private String subVisitCategory;
    private String visitCategory;
    private String pregnancyStatus;
    private String followUpForFpMethod;
    private String otherFollowUpForFpMethod;
    private String sideEffects;
    private String otherSideEffects;
    private String IdrsOrCbac;
    private String rCHID;
    private String healthFacilityType;
    private String healthFacilityLocation;
    private String reportFilePath;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private Integer[] fileIDs;

}
