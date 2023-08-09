package com.iemr.hwc.fhir.provider.patient;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import com.iemr.hwc.fhir.service.patient.PatientService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class PatientExtProvider implements IResourceProvider {

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return PatientExt.class;
    }

    @Autowired
    private PatientService patientService;

    @Create()
    public MethodOutcome createPatient(HttpServletRequest theRequest, @ResourceParam PatientExt patientExt) {

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(patientService.createNewPatient(theRequest,patientExt));
        return method;
    }
}