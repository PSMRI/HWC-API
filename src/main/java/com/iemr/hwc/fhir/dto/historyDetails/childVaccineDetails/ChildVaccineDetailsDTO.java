package com.iemr.hwc.fhir.dto.historyDetails.childVaccineDetails;

import lombok.Data;

@Data
public class ChildVaccineDetailsDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private String[] childOptionalVaccineList;
}
