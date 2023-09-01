package com.iemr.hwc.fhir.dto.visitDetailsMain.adherence;

import lombok.Data;

@Data
public class AdherenceDTO {
    private String beneficiaryRegID;
    private String benVisitID;
    private Integer providerServiceMapID;
    private Boolean toDrugs;
    private String drugReason;
    private Boolean toReferral;
    private String referralReason;
    private String progress;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
