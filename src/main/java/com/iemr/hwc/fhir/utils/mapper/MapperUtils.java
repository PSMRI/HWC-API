package com.iemr.hwc.fhir.utils.mapper;

import com.iemr.hwc.fhir.dto.benDemographics.DemographicsDTO;
import com.iemr.hwc.fhir.dto.benPhone.PhoneDetailsDTO;
import com.iemr.hwc.fhir.dto.beneficiary.BeneficiaryDTO;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring", imports = {Arrays.class})
public interface MapperUtils {

    @Mappings({@Mapping(target = "firstName",expression = "java(MapperMethods.getFirstName(patientExt))"),
            @Mapping(target = "lastName",expression = "java(MapperMethods.getLastName(patientExt))"),
            @Mapping(target = "fatherName", expression = "java(patientExt.getFatherName().asStringValue())"),
            @Mapping(target = "spouseName", expression = "java(patientExt.getSpouseName().asStringValue())"),
            @Mapping(target = "ageAtMarriage", expression = "java(patientExt.getAgeAtMarriage().toString())"),
            @Mapping(target = "providerServiceMapId", expression = "java(Integer.parseInt(patientExt.getProviderServiceMapId().asStringValue()))"),
            @Mapping(target = "parkingPlaceID", expression = "java(Integer.parseInt(patientExt.getParkingPlaceID().asStringValue()))"),
            @Mapping(target = "vanID", expression = "java(Integer.parseInt(patientExt.getVanID().asStringValue()))"),
            @Mapping(target = "createdBy", expression = "java(patientExt.getCreatedBy().asStringValue())"),
            @Mapping(target = "genderName", source = "patientExt.gender"),
            @Mapping(target = "genderID", expression = "java(MapperMethods.getGenderID(patientExt))"),
            @Mapping(target = "DOB", expression = "java(MapperMethods.getTimeStampFromDate(patientExt.getBirthDate()))"),
            @Mapping(target = "maritalStatusName", expression = "java(MapperMethods.getMaritalStatusName(patientExt))"),
            @Mapping(target = "maritalStatusID", expression = "java(MapperMethods.getMaritalStatusID(patientExt))"),
            @Mapping(target = "i_bendemographics",expression = "java(patientResourceToBenDemographicsDTOMapping(patientExt))"),
            @Mapping(target = "benPhoneMaps",expression = "java(Arrays.asList(resourceToPhoneDTOMapping(patientExt)))"),
            @Mapping(target = "beneficiaryIdentities", expression = "java(MapperMethods.IdentityMapping(patientExt))")
    })
    BeneficiaryDTO patientResourceToBeneficiaryDTOMapping(PatientExt patientExt);

    @Mappings({@Mapping(target = "phoneNo",expression = "java(MapperMethods.getTelecomNo(patientExt))"),
            @Mapping(target = "parkingPlaceID", expression = "java(Integer.parseInt(patientExt.getParkingPlaceID().asStringValue()))"),
            @Mapping(target = "vanID", expression = "java(Integer.parseInt(patientExt.getVanID().asStringValue()))"),
            @Mapping(target = "createdBy", expression = "java(patientExt.getCreatedBy().asStringValue())")
    })
    PhoneDetailsDTO resourceToPhoneDTOMapping(PatientExt patientExt);


    @Mappings({@Mapping(target= "stateID", expression = "java(Integer.parseInt(patientExt.getState().getCode()))"),
            @Mapping(target= "stateName", expression = "java(patientExt.getState().getDisplay())"),
            @Mapping(target= "districtID", expression = "java(Integer.parseInt(patientExt.getDistrict().getCode()))"),
            @Mapping(target= "districtName", expression = "java(patientExt.getDistrict().getDisplay())"),
            @Mapping(target= "blockID", expression = "java(Integer.parseInt(patientExt.getBlock().getCode()))"),
            @Mapping(target= "blockName", expression = "java(patientExt.getBlock().getDisplay())"),
            @Mapping(target= "districtBranchID", expression = "java(Integer.parseInt(patientExt.getDistrictBranch().getCode()))"),
            @Mapping(target= "districtBranchName", expression = "java(patientExt.getDistrictBranch().getDisplay())"),
            @Mapping(target= "religionID", expression = "java(patientExt.getReligion().getCode())"),
            @Mapping(target= "religionName", expression = "java(patientExt.getReligion().getDisplay())"),
            @Mapping(target= "communityID", expression = "java(Integer.parseInt(patientExt.getCommunity().getCode()))"),
            @Mapping(target= "communityName", expression = "java(patientExt.getCommunity().getDisplay())"),
            @Mapping(target = "parkingPlaceID",expression = "java(Integer.parseInt(patientExt.getParkingPlaceID().asStringValue()))"),
            @Mapping(target= "countryID", constant = "1"),
            @Mapping(target= "countryName", constant = "India")
    })
    DemographicsDTO patientResourceToBenDemographicsDTOMapping(PatientExt patientExt);



}
