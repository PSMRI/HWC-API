package com.iemr.hwc.fhir.service.observation;

import com.iemr.hwc.fhir.model.observation.ObservationExt;
import javax.servlet.http.HttpServletRequest;

public interface ObservationService {
    ObservationExt createObservation(HttpServletRequest theRequest, ObservationExt observationExt) throws Exception;
}
