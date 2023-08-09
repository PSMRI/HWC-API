package com.iemr.hwc.fhir.dto.historyDetails.immunizationHistory;

import lombok.Data;

@Data
public class ImmunizationDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private String processed;
    private String deleted;
    private String[] immunizationList;
}
