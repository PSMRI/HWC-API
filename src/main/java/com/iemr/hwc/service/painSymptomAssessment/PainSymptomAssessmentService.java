package com.iemr.hwc.service.painSymptomAssessment;

import com.iemr.hwc.data.painSymptom.PainSymptomAssessmentDTO;
import com.iemr.hwc.data.painSymptom.PainSymptomData;

import java.util.List;

public interface PainSymptomAssessmentService {
    String saveAll(List<PainSymptomAssessmentDTO> painList);

    List<PainSymptomAssessmentDTO> getPain(String user);
}
