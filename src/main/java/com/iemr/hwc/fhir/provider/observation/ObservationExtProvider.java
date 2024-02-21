package com.iemr.hwc.fhir.provider.observation;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.iemr.hwc.fhir.model.observation.ObservationExt;
import com.iemr.hwc.fhir.service.observation.ObservationService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ObservationExtProvider implements IResourceProvider {

    @Autowired
    private ObservationService observationService;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return ObservationExt.class;
    }

    @Create()
    public MethodOutcome createObservation(HttpServletRequest theRequest, @ResourceParam ObservationExt observationExt) throws Exception{

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(observationService.createObservation(theRequest,observationExt));
        return method;
    }
}
