package com.iemr.hwc.fhir.dto.covidVaccineStatus;

import lombok.Data;

@Data
public class CovidVaccineStatusDTO {
    private String beneficiaryRegID;
    private String covidVSID;
    private String covidVaccineTypeID;
    private String doseTypeID;
    private String vaccineStatus;
    private Integer providerServiceMapID;
    private Boolean deleted;
    private String createdBy;
    private String modifiedBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
