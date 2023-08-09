package com.iemr.hwc.fhir.utils.validation;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import com.iemr.hwc.fhir.model.encounter.EncounterExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class EncounterValidation {
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void encounterResourceValidator(EncounterExt encounterExt) throws UnprocessableEntityException {
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
