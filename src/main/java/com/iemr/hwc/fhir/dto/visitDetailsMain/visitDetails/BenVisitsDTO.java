package com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails;

import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import lombok.Data;

@Data
public class BenVisitsDTO {
    private BeneficiaryVisitDetail benVisitDetails;
    private Integer sessionID;
    private Long beneficiaryID;
    private Long benFlowID;
}
