package com.iemr.hwc.fhir.dto.mandatoryFieldsDTO;

import lombok.Data;

@Data
public class MandatoryFieldsDTO {
    private String beneficiaryRegID;
    private String benFlowID;
    private String beneficiaryID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String visitCode;
    private String createdBy;
    private String modifiedBy;
    private String sessionID;
    private Integer vanID;
    private Integer parkingPlaceID;
}
