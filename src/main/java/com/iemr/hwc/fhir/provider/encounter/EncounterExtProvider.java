package com.iemr.hwc.fhir.provider.encounter;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.iemr.hwc.fhir.model.encounter.EncounterExt;
import com.iemr.hwc.fhir.service.encounter.EncounterService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class EncounterExtProvider implements IResourceProvider {

    @Autowired
    private EncounterService encounterService;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return EncounterExt.class;
    }

    @Create()
    public MethodOutcome createEncounter(HttpServletRequest theRequest, @ResourceParam EncounterExt encounterExt) {

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(encounterService.createNewEncounter(theRequest,encounterExt));
        return method;
    }
}
