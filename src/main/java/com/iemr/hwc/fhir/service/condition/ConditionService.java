package com.iemr.hwc.fhir.service.condition;

import com.iemr.hwc.fhir.model.condition.ConditionExt;
import javax.servlet.http.HttpServletRequest;

public interface ConditionService {
    ConditionExt addNewChiefComplaint(HttpServletRequest theRequest, ConditionExt conditionExt) throws Exception;
}
