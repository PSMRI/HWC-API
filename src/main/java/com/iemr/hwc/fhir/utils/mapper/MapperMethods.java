package com.iemr.hwc.fhir.utils.mapper;

import com.iemr.hwc.fhir.dto.benIdentities.GovtIdentitiesDTO;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapperMethods {

    public static String getFirstName(PatientExt patientExt) {
            return patientExt.getNameFirstRep().getGiven().get(0).toString();
    }

    public static String getLastName(PatientExt patientExt){
        if (patientExt.hasName() && patientExt.getNameFirstRep().hasFamily()) {
            return patientExt.getNameFirstRep().getFamily();
        }
        else return null;
    }

    public static Integer getGenderID(PatientExt patientExt){
            String gender = patientExt.getGender().toString();
            if (gender.equalsIgnoreCase("male")) {
                return 1;
            } else if (gender.equalsIgnoreCase("female")) {
                return 2;
            } else {
                return 3;
            }
    }

    public static String getMaritalStatusName(PatientExt patientExt){
        if (patientExt.hasMaritalStatus() && patientExt.getMaritalStatus().hasCoding()
                && patientExt.getMaritalStatus().getCodingFirstRep().hasDisplay())
        {
            return patientExt.getMaritalStatus().getCodingFirstRep().getDisplay();
        }
        else return null;
    }


    public static Integer getMaritalStatusID(PatientExt patientExt){
        if (patientExt.hasMaritalStatus() && patientExt.getMaritalStatus().hasCoding()
                && patientExt.getMaritalStatus().getCodingFirstRep().hasCode())
        {
            return Integer.valueOf(patientExt.getMaritalStatus().getCodingFirstRep().getCode());
        }
        else return null;
    }

    public static String getTimeStampFromDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return dateFormat.format(date);
    }

    public static String getTelecomNo(PatientExt patientExt){
        return patientExt.getTelecomFirstRep().getValue();
    }

    public static List<GovtIdentitiesDTO> IdentityMapping(PatientExt patientExt){
        List<GovtIdentitiesDTO> govtIds = new ArrayList<>();
        if (!patientExt.getGovtIdentityType().isEmpty() && !patientExt.getGovtIdentityNo().isEmpty()){
            GovtIdentitiesDTO id1 = new GovtIdentitiesDTO();
            id1.setGovtIdentityTypeID(Integer.parseInt(patientExt.getGovtIdentityType().getCode()));
            id1.setGovtIdentityTypeName(patientExt.getGovtIdentityType().getDisplay());
            id1.setGovtIdentityNo(patientExt.getGovtIdentityNo().asStringValue());
            id1.setCreatedBy(patientExt.getCreatedBy().asStringValue());
            govtIds.add(id1);
        }
        if (!patientExt.getGovtHealthProgramType().isEmpty() && !patientExt.getGovtHealthProgramId().isEmpty()){
            GovtIdentitiesDTO id2 = new GovtIdentitiesDTO();
            id2.setGovtIdentityTypeID(Integer.parseInt(patientExt.getGovtHealthProgramType().getCode()));
            id2.setGovtIdentityTypeName(patientExt.getGovtHealthProgramType().getDisplay());
            id2.setGovtIdentityNo(patientExt.getGovtHealthProgramId().asStringValue());
            id2.setCreatedBy(patientExt.getCreatedBy().asStringValue());
            govtIds.add(id2);
        }
        return govtIds;
    }
}
