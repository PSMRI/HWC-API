package com.iemr.hwc.fhir.utils.validation;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class Validations{

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void patientResourceValidator(PatientExt patientExt) throws UnprocessableEntityException{
        ArrayList<String> errMessages = new ArrayList<>();

        if (!patientExt.hasName() || !patientExt.getNameFirstRep().hasGiven()) {
            logger.error("Error while validating patient resource. First name of beneficiary is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'given' in 'name' missing");
        }

        if (!patientExt.hasGender()) {
            logger.error("Error while validating patient resource. Gender of beneficiary is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'gender' missing");
        }

        if (!patientExt.hasBirthDate()) {
            logger.error("Error while validating patient resource. BirthDate of beneficiary is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'birthDate' missing");
        }

        if (!patientExt.hasTelecom() || !patientExt.getTelecomFirstRep().hasValue()) {
            logger.error("Error while validating patient resource. Phone number of beneficiary is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'value'(phone number) in 'telecom' missing");
        }

        if (patientExt.getCreatedBy().isEmpty()) {
            logger.error("Error while validating patient resource. CreatedBy is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'createdBy' missing");
        }

        if (patientExt.getProviderServiceMapId().isEmpty()) {
            logger.error("Error while validating patient resource. providerServiceMapId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'providerServiceMapId' missing");
        }

        if (patientExt.getParkingPlaceID().isEmpty()) {
            logger.error("Error while validating patient resource. parkingPlaceId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'parkingPlaceId' missing");
        }

        if (patientExt.getVanID().isEmpty()) {
            logger.error("Error while validating patient resource. vanId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'vanId' missing");
        }

        if (patientExt.getState().isEmpty() || !patientExt.getState().hasCode() || !patientExt.getState().hasDisplay()) {
            logger.error("Error while validating patient resource. State details of beneficiary MISSING");
            errMessages.add("Mandatory extension 'state' or its sub-fields(code or display) missing");
        }

        if (patientExt.getDistrict().isEmpty() || !patientExt.getDistrict().hasCode() || !patientExt.getDistrict().hasDisplay()) {
            logger.error("Error while validating patient resource. District details of beneficiary MISSING");
            errMessages.add("Mandatory extension 'district' or its sub-fields(code or display) missing");
        }

        if (patientExt.getBlock().isEmpty() || !patientExt.getBlock().hasCode() || !patientExt.getBlock().hasDisplay()) {
            logger.error("Error while validating patient resource. Block details of beneficiary MISSING");
            errMessages.add("Mandatory extension 'block' or its sub-fields(code or display) missing");
        }

        if (patientExt.getDistrictBranch().isEmpty() || !patientExt.getDistrictBranch().hasCode() || !patientExt.getDistrictBranch().hasDisplay()) {
            logger.error("Error while validating patient resource. Village/DistrictBranch details of beneficiary MISSING");
            errMessages.add("Mandatory extension 'districtBranch' or its sub-fields(code or display) missing");
        }

        if (patientExt.getCommunity().isEmpty() || !patientExt.getCommunity().hasCode() || !patientExt.getCommunity().hasDisplay()) {
            logger.error("Error while validating patient resource. Community details of beneficiary MISSING");
            errMessages.add("Mandatory extension 'community' or its sub-fields(code or display) missing");
        }

        if(!errMessages.isEmpty()){
            throw new UnprocessableEntityException(errMessages.toArray(new String[0]));
        }
    }
}
