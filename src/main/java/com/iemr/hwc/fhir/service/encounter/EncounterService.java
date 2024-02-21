package com.iemr.hwc.fhir.service.encounter;


import com.iemr.hwc.fhir.model.encounter.EncounterExt;
import jakarta.servlet.http.HttpServletRequest;

public interface EncounterService {
    EncounterExt createNewEncounter(HttpServletRequest theRequest,EncounterExt encounterExt);
}
