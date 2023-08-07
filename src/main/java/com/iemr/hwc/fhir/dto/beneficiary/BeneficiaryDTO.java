package com.iemr.hwc.fhir.dto.beneficiary;

import com.google.gson.annotations.SerializedName;
import com.iemr.hwc.fhir.dto.beneficiary.benDemographics.DemographicsDTO;
import com.iemr.hwc.fhir.dto.beneficiary.benIdentities.GovtIdentitiesDTO;
import com.iemr.hwc.fhir.dto.beneficiary.benPhone.PhoneDetailsDTO;
import lombok.Data;

import java.util.List;

@Data
public class BeneficiaryDTO {
    private String firstName;
    private String lastName;
    private String fatherName;
    private String spouseName;
    private String genderName;
    private Integer genderID;
    @SerializedName("dOB")
    private String dOB;
    private Integer maritalStatusID;
    private String maritalStatusName;
    private String ageAtMarriage;
    private Integer providerServiceMapId;
    @SerializedName("providerServiceMapID")
    private Integer providerServiceMapIdCopy;
    private DemographicsDTO i_bendemographics;
    private List<PhoneDetailsDTO> benPhoneMaps;
    private List<GovtIdentitiesDTO> beneficiaryIdentities;
    private Integer vanID;
    private Integer parkingPlaceID;
    private String createdBy;

}
