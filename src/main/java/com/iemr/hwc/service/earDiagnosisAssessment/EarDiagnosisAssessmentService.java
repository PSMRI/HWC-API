package com.iemr.hwc.service.earDiagnosisAssessment;

import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessmentDTO;

import java.util.List;

public interface EarDiagnosisAssessmentService {

    List<EarDiagnosisAssessmentDTO> saveAll(List<EarDiagnosisAssessmentDTO> dtos);

    List<EarDiagnosisAssessmentDTO> getAll(String userName);
}