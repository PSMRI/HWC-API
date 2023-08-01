package com.iemr.hwc.fhir.dto.benDemographics;

import lombok.Data;

@Data
public class DemographicsDTO {
    private Integer countryID;
    private String countryName;
    private Integer stateID;
    private String stateName;
    private Integer districtID;
    private String districtName;
    private String religionID;
    private String religionName;
    private Integer communityID;
    private String communityName;
    private Integer blockID;
    private String blockName;
    private Integer districtBranchID;
    private String districtBranchName;
    private Integer parkingPlaceID;
}
