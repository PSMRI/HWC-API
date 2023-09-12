package com.iemr.hwc.fhir.dto.historyDetails.personalHistory;

import lombok.Data;

import java.util.List;

@Data
public class PersonalHistoryDTO {
    private String dietaryType;
    private String physicalActivityType;
    private String riskySexualPracticesStatus;
    private String tobaccoUseStatus;
    private List<TobaccoListDTO> tobaccoList;
    private List<AlcoholListDTO> alcoholList;
    private List<AllergicListDTO> allergicList;
    private String alcoholIntakeStatus;
    private String allergyStatus;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
