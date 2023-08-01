package com.iemr.hwc.fhir.provider.patient;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.fhir.dto.beneficiary.BeneficiaryDTO;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import com.iemr.hwc.fhir.utils.mapper.MapperUtils;
import com.iemr.hwc.fhir.utils.validation.Validations;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class PatientExtProvider implements IResourceProvider {

    public MapperUtils mapper = Mappers.getMapper(MapperUtils.class);
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private RegistrarServiceImpl registrarServiceImpl;

    @Autowired
    private Validations validations;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return PatientExt.class;
    }

    @Create()
    public MethodOutcome createPatient(HttpServletRequest theRequest, @ResourceParam PatientExt patientExt) throws Exception {

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);

        //Validating the resource for mandatory fields
        validations.patientResourceValidator(patientExt);

        BeneficiaryDTO benDTO = mapper.patientResourceToBeneficiaryDTOMapping(patientExt);

        String benDTOGson = new GsonBuilder().create().toJson(benDTO);

        String authorization = theRequest.getHeader("Authorization");

        String registeredBeneficiary = registrarServiceImpl.registerBeneficiary(benDTOGson, authorization);

        JsonObject registeredBenJson = new JsonParser().parse(registeredBeneficiary).getAsJsonObject();
        String benID = registeredBenJson.getAsJsonObject("data").get("response").getAsString();
        String[] arrOfResponse = benID.split(":");
        patientExt.setId(arrOfResponse[arrOfResponse.length - 1].trim());

        method.setResource(patientExt);
        return method;
    }
}