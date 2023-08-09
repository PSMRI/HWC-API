package com.iemr.hwc.fhir.utils.validation;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import com.iemr.hwc.fhir.model.encounter.EncounterExt;
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

    public void encounterResourceValidator(EncounterExt encounterExt) throws UnprocessableEntityException{
        ArrayList<String> errMessages = new ArrayList<>();

        if (encounterExt.getBeneficiaryID().isEmpty() && encounterExt.getBeneficiaryRegID().isEmpty()) {
            logger.error("Error while validating encounter resource. Either BeneficiaryID or BenRegID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'beneficiaryID/benRegID' missing");
        }

        if (encounterExt.getBenFlowID().isEmpty()) {
            logger.error("Error while validating encounter resource. BenFlowID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'benFlowID' missing");
        }

        if (encounterExt.getSessionID().isEmpty()) {
            logger.error("Error while validating encounter resource. SessionID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'sessionID' missing");
        }


        if (encounterExt.getCreatedBy().isEmpty()) {
            logger.error("Error while validating encounter resource. CreatedBy is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'createdBy' missing");
        }

        if (encounterExt.getProviderServiceMapId().isEmpty()) {
            logger.error("Error while validating encounter resource. providerServiceMapId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'providerServiceMapId' missing");
        }

        if (encounterExt.getParkingPlaceID().isEmpty()) {
            logger.error("Error while validating encounter resource. parkingPlaceId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'parkingPlaceId' missing");
        }

        if (encounterExt.getVanID().isEmpty()) {
            logger.error("Error while validating encounter resource. vanId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'vanId' missing");
        }

        //Validations for Visit reason and visit Category also.
        if (!encounterExt.hasType() || !encounterExt.getTypeFirstRep().hasCoding() || !encounterExt.getTypeFirstRep().getCodingFirstRep().hasDisplay()) {
            logger.error("Error while validating encounter resource. 'display' in 'type' (visitCategory) is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'display' in 'type' (visitCategory) missing");
        }

        if (!encounterExt.hasReasonCode() || !encounterExt.getReasonCodeFirstRep().hasText()) {
            logger.error("Error while validating encounter resource. 'text' in 'reasonCode' (visitReason) is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'text' in 'reasonCode' (visitReason) missing");
        }

        if(!errMessages.isEmpty()){
            throw new UnprocessableEntityException(errMessages.toArray(new String[0]));
        }
    }

}
