package com.iemr.hwc.service.noiseDignosisAssessgment;

import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessmentDTO;

import java.util.List;

public interface NoseDiagnosisAssessmentService {

    List<NoseDiagnosisAssessmentDTO> saveAll(List<NoseDiagnosisAssessmentDTO> dtos, Integer userId);

    List<NoseDiagnosisAssessmentDTO> getAll(Integer userId);
}
