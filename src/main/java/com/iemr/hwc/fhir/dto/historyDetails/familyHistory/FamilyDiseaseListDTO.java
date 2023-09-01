package com.iemr.hwc.fhir.dto.historyDetails.familyHistory;

import lombok.Data;

@Data
public class FamilyDiseaseListDTO {
    private String diseaseTypeID;
    private String diseaseType;
    private String otherDiseaseType;
    private String snomedCode;
    private String snomedTerm;
    private String[] familyMembers;
}
