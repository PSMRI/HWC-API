package com.iemr.hwc.service.noiseDignosisAssessgment;

import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessmentDTO;

import java.util.List;

public interface NoseDiagnosisAssessmentService {

    String saveAll(List<NoseDiagnosisAssessmentDTO> dtos, String user);

    List<NoseDiagnosisAssessmentDTO> getAll(String user);
}