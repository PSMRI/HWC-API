package com.iemr.hwc.fhir.dto.historyDetails.pastHistory;

import lombok.Data;
import java.util.List;

@Data
public class PastHistoryDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String visitCode;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private List<PastIllnessDTO> pastIllness;
    private List<PastSurgeryDTO> pastSurgery;
}
