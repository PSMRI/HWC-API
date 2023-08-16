package com.iemr.hwc.fhir.utils.validation;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import com.iemr.hwc.fhir.model.condition.ConditionExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ConditionValidation {
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    public void conditionResourceValidator(ConditionExt conditionExt) throws UnprocessableEntityException {
        ArrayList<String> errMessages = new ArrayList<>();

        //Todo - If Any of these 2 Id is being sent in 'subject', then validate that.
        if (conditionExt.getBeneficiaryID().isEmpty() && conditionExt.getBeneficiaryRegID().isEmpty()) {
            logger.error("Error while validating Condition resource. Either BeneficiaryID or BenRegID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'beneficiaryID/benRegID' missing");
        }

        if (conditionExt.getBenFlowID().isEmpty()) {
            logger.error("Error while validating Condition resource. BenFlowID is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'benFlowID' missing");
        }

        if (conditionExt.getCreatedBy().isEmpty()) {
            logger.error("Error while validating Condition resource. CreatedBy is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'createdBy' missing");
        }

        if (conditionExt.getProviderServiceMapId().isEmpty()) {
            logger.error("Error while validating Condition resource. providerServiceMapId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'providerServiceMapId' missing");
        }

        if (conditionExt.getParkingPlaceID().isEmpty()) {
            logger.error("Error while validating Condition resource. parkingPlaceId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'parkingPlaceId' missing");
        }

        if (conditionExt.getVanID().isEmpty()) {
            logger.error("Error while validating Condition resource. vanId is a mandatory field and is MISSING");
            errMessages.add("Mandatory extension 'vanId' missing");
        }

        //Validations for Chief complaint, chief complaintID .
        if (!conditionExt.hasCode() || !conditionExt.getCode().hasCoding() || !conditionExt.getCode().getCodingFirstRep().hasCode() || !conditionExt.getCode().getCodingFirstRep().hasDisplay()) {
            logger.error("Error while validating Condition resource. 'display' or 'code' in 'code' (chiefComplaint or chiefComplaintID) is a mandatory field and is MISSING");
            errMessages.add("Mandatory field 'display' or 'code' in 'code' (chiefComplaint or chiefComplaintID) missing");
        }

        //Check whether both duration and its unit are present. If not then both should be absent
        if (!conditionExt.getDuration().isEmpty() && (!conditionExt.getDuration().hasCode() || !conditionExt.getDuration().hasDisplay())) {
            logger.error("Error while validating Condition resource. 'code'(duration unit) & 'display'(duration value) both should be present in duration extension. One of them is MISSING");
            errMessages.add("Either 'code'(duration unit) or 'display'(duration value) missing for 'duration' extension");
        }

        if(!errMessages.isEmpty()){
            throw new UnprocessableEntityException(errMessages.toArray(new String[0]));
        }
    }
}
