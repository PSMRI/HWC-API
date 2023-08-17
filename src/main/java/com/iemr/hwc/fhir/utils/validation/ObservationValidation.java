package com.iemr.hwc.fhir.utils.validation;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import com.iemr.hwc.fhir.model.observation.ObservationExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ObservationValidation {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void vitalsObservationValidator(ObservationExt observationExt) throws UnprocessableEntityException {
        ArrayList<String> errMessages = new ArrayList<>();

        //Todo - If Any of these 2 Id is being sent in 'subject', then validate that.
        //Todo - Right now validating benRegID and not beneficiaryID. If only latter is sent in payload, then validate accordingly.
        if (observationExt.getBeneficiaryRegID().isEmpty()) {
            logger.error("Error while validating Observation resource. BenRegID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'benRegID' missing");
        }

        if (observationExt.getBenFlowID().isEmpty()) {
            logger.error("Error while validating Observation resource. BenFlowID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'benFlowID' missing");
        }

        if (observationExt.getModifiedBy().isEmpty()) {
            logger.error("Error while validating Observation resource. ModifiedBy is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'modifiedBy' missing");
        }

        if(!errMessages.isEmpty()){
            throw new UnprocessableEntityException(errMessages.toArray(new String[0]));
        }
    }

    public void historyObservationValidator(ObservationExt observationExt) throws UnprocessableEntityException{
        ArrayList<String> errMessages = new ArrayList<>();

        //Todo - If Any of these 2 Id is being sent in 'subject', then validate that.
        //Todo - Right now validating benRegID and not beneficiaryID. If only latter is sent in payload, then validate accordingly.
        if (observationExt.getBeneficiaryRegID().isEmpty()) {
            logger.error("Error while validating Observation resource. BenRegID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'benRegID' missing");
        }

        if (observationExt.getBenFlowID().isEmpty()) {
            logger.error("Error while validating Observation resource. BenFlowID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'benFlowID' missing");
        }

        if (observationExt.getCreatedBy().isEmpty()) {
            logger.error("Error while validating Observation resource. CreatedBy is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'createdBy' missing");
        }

        if (observationExt.getProviderServiceMapId().isEmpty()) {
            logger.error("Error while validating Observation resource. providerServiceMapId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'providerServiceMapId' missing");
        }

        if (observationExt.getParkingPlaceID().isEmpty()) {
            logger.error("Error while validating Observation resource. parkingPlaceId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'parkingPlaceId' missing");
        }

        if (observationExt.getVanID().isEmpty()) {
            logger.error("Error while validating Observation resource. vanId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'vanId' missing");
        }

        if(!errMessages.isEmpty()){
            throw new UnprocessableEntityException(errMessages.toArray(new String[0]));
        }
    }
}
