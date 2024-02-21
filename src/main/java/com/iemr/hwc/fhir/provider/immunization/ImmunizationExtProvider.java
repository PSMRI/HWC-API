package com.iemr.hwc.fhir.provider.immunization;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.iemr.hwc.fhir.model.immunization.ImmunizationExt;
import com.iemr.hwc.fhir.service.immunization.ImmunizationService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ImmunizationExtProvider implements IResourceProvider {

    @Autowired
    private ImmunizationService immunizationService;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return ImmunizationExt.class;
    }

    @Create()
    public MethodOutcome createImmunization(HttpServletRequest theRequest, @ResourceParam ImmunizationExt immunizationExt) throws Exception {

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(immunizationService.addOrUpdateImmunization(theRequest,immunizationExt));
        return method;
    }

    @Update()
    public MethodOutcome updateImmunization(HttpServletRequest theRequest, @ResourceParam ImmunizationExt immunizationExt) throws Exception {

        MethodOutcome method = new MethodOutcome();
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(immunizationService.addOrUpdateImmunization(theRequest,immunizationExt));
        return method;
    }
}
