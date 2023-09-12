package com.iemr.hwc.fhir.service.immunization;

import com.iemr.hwc.fhir.model.immunization.ImmunizationExt;
import javax.servlet.http.HttpServletRequest;

public interface ImmunizationService {
    ImmunizationExt addOrUpdateImmunization(HttpServletRequest theRequest, ImmunizationExt immunizationExt) throws Exception;
}
