package com.iemr.hwc.fhir.dto.historyDetails.personalHistory;

import lombok.Data;

@Data
public class AllergicReactionsDTO {
    private String allergicReactionTypeID;
    private String name;
    private Boolean deleted;
    private String processed;
    private String createdBy;
    private String createdDate;
    private String lastModDate;
}
