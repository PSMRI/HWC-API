package com.iemr.hwc.fhir.dto.historyDetails.medicationHistory;

import lombok.Data;

import java.util.List;

@Data
public class MedicationHistoryDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private List<MedicationListDTO> medicationHistoryList;
}
