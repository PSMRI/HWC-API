package com.iemr.hwc.fhir.dto.historyDetails.familyHistory;

import lombok.Data;

import java.util.List;

@Data
public class FamilyHistoryDTO {
    private Boolean isGeneticDisorder;
    private String geneticDisorder;
    private Boolean isConsanguineousMarrige;
    private List<FamilyDiseaseListDTO> familyDiseaseList;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
