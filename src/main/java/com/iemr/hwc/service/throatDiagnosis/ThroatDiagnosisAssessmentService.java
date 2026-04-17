package com.iemr.hwc.service.throatDiagnosis;

import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessmentDTO;

import java.util.List;

public interface ThroatDiagnosisAssessmentService {

    String saveAll(List<ThroatDiagnosisAssessmentDTO> dtos, String user);

    List<ThroatDiagnosisAssessmentDTO> getAll(String user);
}