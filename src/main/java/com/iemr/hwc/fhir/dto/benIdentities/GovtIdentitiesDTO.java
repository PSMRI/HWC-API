package com.iemr.hwc.fhir.dto.benIdentities;

import lombok.Data;

@Data
public class GovtIdentitiesDTO {
    private String govtIdentityNo;
    private Integer govtIdentityTypeID;
    private String govtIdentityTypeName;
    private String createdBy;
}
