package com.iemr.hwc.fhir.dto.historyDetails.personalHistory;

import lombok.Data;
import java.util.List;

@Data
public class AllergicListDTO {
    private String allergyType;
    private String allergyName;
    private String snomedTerm;
    private String snomedCode;
    private String otherAllergicReaction;
    private Boolean enableOtherAllergy;
    private List<AllergicReactionsDTO> typeOfAllergicReactions;
}
