package com.iemr.hwc.service.throatDiagnosis;

import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessmentDTO;

import java.util.List;

public interface ThroatDiagnosisAssessmentService {

    List<ThroatDiagnosisAssessmentDTO> saveAll(List<ThroatDiagnosisAssessmentDTO> dtos, Integer userId);

    List<ThroatDiagnosisAssessmentDTO> getAll(Integer userId);
}
