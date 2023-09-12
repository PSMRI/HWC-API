package com.iemr.hwc.fhir.dto.nurseForm;

import com.iemr.hwc.fhir.dto.examinationDetails.ExaminationDetailsMainDTO;
import com.iemr.hwc.fhir.dto.historyDetails.HistoryDetailsMainDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.VisitDetailsMainDTO;
import com.iemr.hwc.fhir.dto.vitalDetails.VitalDetailsDTO;
import lombok.Data;

@Data
public class NurseFormDTO {
    private VisitDetailsMainDTO visitDetails;
    private VitalDetailsDTO vitalDetails;
    private HistoryDetailsMainDTO historyDetails;
    private ExaminationDetailsMainDTO examinationDetails;
    private String benFlowID;
    private String beneficiaryID;
    private String sessionID;
    private Integer parkingPlaceID;
    private Integer vanID;
    private String serviceID;
    private String createdBy;
    private String tcRequest;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;

}
