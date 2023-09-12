package com.iemr.hwc.fhir.utils.validation;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import com.iemr.hwc.fhir.model.immunization.ImmunizationExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ImmunizationValidation {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    public void immunizationResourceValidator(ImmunizationExt immunizationExt) throws UnprocessableEntityException {
        ArrayList<String> errMessages = new ArrayList<>();

        if (immunizationExt.getCreatedBy().isEmpty()) {
            logger.error("Error while validating Immunization resource. CreatedBy is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'createdBy' missing");
        }

        if (immunizationExt.getProviderServiceMapId().isEmpty()) {
            logger.error("Error while validating Immunization resource. providerServiceMapId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'providerServiceMapId' missing");
        }

        if (immunizationExt.getParkingPlaceID().isEmpty()) {
            logger.error("Error while validating Immunization resource. parkingPlaceId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'parkingPlaceId' missing");
        }

        if (immunizationExt.getVanID().isEmpty()) {
            logger.error("Error while validating Immunization resource. vanId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'vanId' missing");
        }

        //If vaccination is done then validations for type of vaccine, type of dose
        if (immunizationExt.getStatus().getDisplay().equalsIgnoreCase("completed")) {
            if (!immunizationExt.getVaccineCode().hasText()) {
                logger.error("Error while validating Immunization resource. 'text' in 'vaccineCode' (Covid vaccine type ID) is a mandatory field and is MISSING");
                errMessages.add("Mandatory field 'text' in 'vaccineCode' (Covid vaccine type ID) missing");
            }
            if (!immunizationExt.hasProtocolApplied()) {
                logger.error("Error while validating Immunization resource. 'doseNumber' in 'protocolApplied' (Dose type ID) is a mandatory field and is MISSING");
                errMessages.add("Mandatory field 'doseNumber' in 'protocolApplied' (Dose type ID) missing");
            }
        }

        //If vaccination is not done then
        if (immunizationExt.getStatus().getDisplay().equalsIgnoreCase("not-done")) {
            if (immunizationExt.getVaccineCode().hasText()) {
                logger.error("Error while validating Immunization resource. 'text' in 'vaccineCode' (Covid vaccine type ID) should not be present for vaccination status 'not-done'");
                errMessages.add("For vaccination status 'not-done' - 'text' in 'vaccineCode' (Covid vaccine type ID) should not be present");
            }
            if (immunizationExt.hasProtocolApplied()) {
                logger.error("Error while validating Immunization resource. 'doseNumber' in 'protocolApplied' (Dose type ID) should not be present for vaccination status 'not-done'");
                errMessages.add("For vaccination status 'not-done' - 'doseNumber' in 'protocolApplied' (Dose type ID) should not be present");
            }
        }

        if(!errMessages.isEmpty()){
            throw new UnprocessableEntityException(errMessages.toArray(new String[0]));
        }
    }
}
