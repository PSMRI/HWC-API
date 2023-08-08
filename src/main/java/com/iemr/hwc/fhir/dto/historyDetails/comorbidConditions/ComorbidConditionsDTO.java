package com.iemr.hwc.fhir.dto.historyDetails.comorbidConditions;

import lombok.Data;
import java.util.List;

@Data
public class ComorbidConditionsDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private List<ConcurrentConditionsDTO> comorbidityConcurrentConditionsList;
}
