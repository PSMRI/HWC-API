package com.iemr.hwc.fhir.service.encounter;

import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.fhir.dto.mandatoryFieldsDTO.MandatoryFieldsDTO;
import com.iemr.hwc.fhir.dto.nurseForm.NurseFormDTO;
import com.iemr.hwc.fhir.model.encounter.EncounterExt;
import com.iemr.hwc.fhir.utils.mapper.MapperUtils;
import com.iemr.hwc.fhir.utils.validation.Validations;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import org.hl7.fhir.r4.model.StringType;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class EncounterServiceImpl implements EncounterService{

    public MapperUtils mapper = Mappers.getMapper(MapperUtils.class);

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private GeneralOPDServiceImpl generalOPDServiceImpl;

    @Autowired
    private Validations validations;

    @Override
    public EncounterExt createNewEncounter(HttpServletRequest theRequest, EncounterExt encounterExt){

        //Todo - Hardcoding sessionID for now, to be removed once added to payload
        if(encounterExt.getSessionID().isEmpty()){
            encounterExt.setSessionID(new StringType("3"));
        }

        //Validating the resource for mandatory fields
        validations.encounterResourceValidator(encounterExt);

        //Todo - Currently implemented considering all relevant IDs(beneficiaryID, benRegID, benFlowID) are coming in payload.
        //Todo - If not, might need to write new APIs to fetch necessary IDs through some sort of logic. And then use those further.
        MandatoryFieldsDTO mandatoryFieldsDTO = mapper.encounterResourceToMandatoryFieldsDTO(encounterExt);

        NurseFormDTO nurseFormDTO = mapper.encounterResourceToNurseFormDTO(encounterExt, mandatoryFieldsDTO);

        String nurseDTOGson = new GsonBuilder().serializeNulls().create().toJson(nurseFormDTO);
        JsonObject nurseFormJson = new JsonParser().parse(nurseDTOGson).getAsJsonObject();

        String authorization = theRequest.getHeader("Authorization");

        try {
            String saveEncounterResponse = generalOPDServiceImpl.saveNurseData(nurseFormJson, authorization);
            Map<String, String> map = new HashMap<>();
            Gson gson = new Gson();
            map = (Map<String, String>) gson.fromJson(saveEncounterResponse, map.getClass());
            encounterExt.setId(map.get("visitCode"));
        }catch (Exception e){
            throw new InternalErrorException("Error occurred while saving visit details - " +  e);
        }
        return encounterExt;
    }
}
