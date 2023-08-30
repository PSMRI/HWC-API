package com.iemr.hwc.fhir.service.patient;

import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.fhir.dto.beneficiary.BeneficiaryDTO;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import com.iemr.hwc.fhir.utils.mapper.MapperUtils;
import com.iemr.hwc.fhir.utils.validation.PatientValidation;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public class PatientServiceImpl implements PatientService{


    public MapperUtils mapper = Mappers.getMapper(MapperUtils.class);
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private RegistrarServiceImpl registrarServiceImpl;

    @Autowired
    private PatientValidation validation;


    @Override
    public PatientExt createNewPatient(HttpServletRequest theRequest, PatientExt patientExt){

        //Validating the resource for mandatory fields
        validation.patientResourceValidator(patientExt);

        BeneficiaryDTO benDTO = mapper.patientResourceToBeneficiaryDTOMapping(patientExt);

        String benDTOGson = new GsonBuilder().create().toJson(benDTO);

        String authorization = theRequest.getHeader("Authorization");
        try {
            String registeredBeneficiary = registrarServiceImpl.registerBeneficiary(benDTOGson, authorization);

            JsonObject registeredBenJson = new JsonParser().parse(registeredBeneficiary).getAsJsonObject();
            String benID = registeredBenJson.getAsJsonObject("data").get("response").getAsString();
            String[] arrOfResponse = benID.split(":");
            patientExt.setId(arrOfResponse[arrOfResponse.length - 1].trim());
        }catch (Exception e){
            throw new InternalErrorException("Error occurred while saving the beneficiary.");
        }

        return patientExt;
    }
}
