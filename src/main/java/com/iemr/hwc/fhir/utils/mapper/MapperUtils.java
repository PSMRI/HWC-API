package com.iemr.hwc.fhir.utils.mapper;

import com.iemr.hwc.fhir.dto.beneficiary.benDemographics.DemographicsDTO;
import com.iemr.hwc.fhir.dto.beneficiary.benPhone.PhoneDetailsDTO;
import com.iemr.hwc.fhir.dto.beneficiary.BeneficiaryDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.ExaminationDetailsMainDTO;
import com.iemr.hwc.fhir.dto.historyDetails.HistoryDetailsMainDTO;
import com.iemr.hwc.fhir.dto.mandatoryFieldsDTO.MandatoryFieldsDTO;
import com.iemr.hwc.fhir.dto.nurseForm.NurseFormDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.VisitDetailsMainDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.adherence.AdherenceDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.chiefComplaints.ChiefComplaintsDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails.VisitDetailsDTO;
import com.iemr.hwc.fhir.dto.vitalDetails.VitalDetailsDTO;
import com.iemr.hwc.fhir.model.condition.ConditionExt;
import com.iemr.hwc.fhir.model.encounter.EncounterExt;
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
            @Mapping(target = "providerServiceMapIdCopy", expression = "java(Integer.parseInt(patientExt.getProviderServiceMapId().asStringValue()))"),
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

    @Mappings({@Mapping(target = "providerServiceMapID", expression = "java(Integer.parseInt(encounterExt.getProviderServiceMapId().asStringValue()))"),
            @Mapping(target = "parkingPlaceID", expression = "java(Integer.parseInt(encounterExt.getParkingPlaceID().asStringValue()))"),
            @Mapping(target = "vanID", expression = "java(Integer.parseInt(encounterExt.getVanID().asStringValue()))"),
            @Mapping(target = "sessionID", expression = "java(encounterExt.getSessionID().asStringValue())"),
            @Mapping(target = "benFlowID", expression = "java(encounterExt.getBenFlowID().asStringValue())"),
            @Mapping(target = "beneficiaryID", expression = "java(encounterExt.getBeneficiaryID().asStringValue())"),
            @Mapping(target = "beneficiaryRegID", expression = "java(encounterExt.getBeneficiaryRegID().asStringValue())"),
            @Mapping(target = "createdBy", expression = "java(encounterExt.getCreatedBy().asStringValue())")
    })
    MandatoryFieldsDTO encounterResourceToMandatoryFieldsDTO(EncounterExt encounterExt);

    @Mappings({@Mapping(target = "providerServiceMapID",source = "mandatoryFieldsDTO.providerServiceMapID"),
            @Mapping(target = "parkingPlaceID", source = "mandatoryFieldsDTO.parkingPlaceID"),
            @Mapping(target = "vanID",source = "mandatoryFieldsDTO.vanID"),
            @Mapping(target = "sessionID",source = "mandatoryFieldsDTO.sessionID"),
            @Mapping(target = "benFlowID",source = "mandatoryFieldsDTO.benFlowID"),
            @Mapping(target = "beneficiaryID", source = "mandatoryFieldsDTO.beneficiaryID"),
            @Mapping(target = "beneficiaryRegID",source = "mandatoryFieldsDTO.beneficiaryRegID"),
            @Mapping(target = "createdBy",source = "mandatoryFieldsDTO.createdBy"),
            @Mapping(target = "visitDetails",expression = "java(encounterResourceToVisitDetailsMainDTO(encounterExt,mandatoryFieldsDTO))"),
            @Mapping(target = "vitalDetails",expression = "java(mandatoryFieldsDTOToVitalDetailsDTO(mandatoryFieldsDTO))"),
            @Mapping(target = "historyDetails", expression = "java(mandatoryFieldsDTOToHistoryDetailsDTO(mandatoryFieldsDTO))"),
            @Mapping(target = "examinationDetails", expression = "java(mandatoryFieldsDTOToExaminationDetailsDTO(mandatoryFieldsDTO))")
    })
    NurseFormDTO encounterResourceToNurseFormDTO(EncounterExt encounterExt, MandatoryFieldsDTO mandatoryFieldsDTO);


    @Mappings({@Mapping(target = "visitDetails",expression = "java(encounterResourceToVisitDetailsDTO(encounterExt,mandatoryFieldsDTO))"),
            @Mapping(target = "chiefComplaints",expression = "java(Arrays.asList(mandatoryFieldsDTOToChiefComplaintsDTO(mandatoryFieldsDTO)))"),
            @Mapping(target = "adherence",expression = "java(mandatoryFieldsDTOToAdherenceDTO(mandatoryFieldsDTO))"),
    })
    VisitDetailsMainDTO encounterResourceToVisitDetailsMainDTO(EncounterExt encounterExt, MandatoryFieldsDTO mandatoryFieldsDTO);

    @Mappings({@Mapping(target = "providerServiceMapID",source = "mandatoryFieldsDTO.providerServiceMapID"),
            @Mapping(target = "beneficiaryRegID",source = "mandatoryFieldsDTO.beneficiaryRegID"),
            @Mapping(target = "vanID",source = "mandatoryFieldsDTO.vanID"),
            @Mapping(target = "parkingPlaceID", source = "mandatoryFieldsDTO.parkingPlaceID"),
            @Mapping(target = "createdBy",source = "mandatoryFieldsDTO.createdBy"),
            @Mapping(target = "visitCategory",expression = "java(encounterExt.getTypeFirstRep().getCodingFirstRep().getDisplay())"),
            @Mapping(target = "visitReason",expression = "java(encounterExt.getReasonCodeFirstRep().getText())"),
            @Mapping(target = "subVisitCategory", expression = "java(MapperMethods.getSubVisitCategoryFromEncounter(encounterExt))")
    })
    VisitDetailsDTO encounterResourceToVisitDetailsDTO(EncounterExt encounterExt,MandatoryFieldsDTO mandatoryFieldsDTO);

    ChiefComplaintsDTO mandatoryFieldsDTOToChiefComplaintsDTO( MandatoryFieldsDTO mandatoryFieldsDTO);

    AdherenceDTO mandatoryFieldsDTOToAdherenceDTO(MandatoryFieldsDTO mandatoryFieldsDTO);

    VitalDetailsDTO mandatoryFieldsDTOToVitalDetailsDTO(MandatoryFieldsDTO mandatoryFieldsDTO);

    HistoryDetailsMainDTO mandatoryFieldsDTOToHistoryDetailsDTO(MandatoryFieldsDTO mandatoryFieldsDTO);

    ExaminationDetailsMainDTO mandatoryFieldsDTOToExaminationDetailsDTO(MandatoryFieldsDTO mandatoryFieldsDTO);

    @Mappings({@Mapping(target = "providerServiceMapID", expression = "java(Integer.parseInt(conditionExt.getProviderServiceMapId().asStringValue()))"),
            @Mapping(target = "parkingPlaceID", expression = "java(Integer.parseInt(conditionExt.getParkingPlaceID().asStringValue()))"),
            @Mapping(target = "vanID", expression = "java(Integer.parseInt(conditionExt.getVanID().asStringValue()))"),
            @Mapping(target = "benFlowID", expression = "java(conditionExt.getBenFlowID().asStringValue())"),
            @Mapping(target = "beneficiaryID", expression = "java(conditionExt.getBeneficiaryID().asStringValue())"),
            @Mapping(target = "beneficiaryRegID", expression = "java(conditionExt.getBeneficiaryRegID().asStringValue())"),
            @Mapping(target = "createdBy", expression = "java(conditionExt.getCreatedBy().asStringValue())")
    })
    MandatoryFieldsDTO conditionResourceToMandatoryFieldsDTO(ConditionExt conditionExt);

    @Mappings({@Mapping(target = "providerServiceMapID",source = "mandatoryFieldsDTO.providerServiceMapID"),
            @Mapping(target = "parkingPlaceID", source = "mandatoryFieldsDTO.parkingPlaceID"),
            @Mapping(target = "vanID",source = "mandatoryFieldsDTO.vanID"),
            @Mapping(target = "benVisitID",source = "mandatoryFieldsDTO.benVisitID"),
            @Mapping(target = "visitCode",source = "mandatoryFieldsDTO.visitCode"),
            @Mapping(target = "beneficiaryRegID",source = "mandatoryFieldsDTO.beneficiaryRegID"),
            @Mapping(target = "createdBy",source = "mandatoryFieldsDTO.createdBy"),
            @Mapping(target = "conceptID",source = "conceptID"),
            @Mapping(target = "chiefComplaint",expression = "java(conditionExt.getCode().getCodingFirstRep().getDisplay())"),
            @Mapping(target = "chiefComplaintID", expression = "java(Integer.parseInt(conditionExt.getCode().getCodingFirstRep().getCode()))"),
            @Mapping(target = "unitOfDuration", expression = "java(conditionExt.getDuration().getCode())"),
            @Mapping(target = "duration", expression = "java(conditionExt.getDuration().getDisplay())"),
            @Mapping(target = "description", expression = "java(conditionExt.getNoteFirstRep().getText())")
    })
    ChiefComplaintsDTO conditionResourceToChiefComplaintDTO(ConditionExt conditionExt, MandatoryFieldsDTO mandatoryFieldsDTO , String conceptID );




}
