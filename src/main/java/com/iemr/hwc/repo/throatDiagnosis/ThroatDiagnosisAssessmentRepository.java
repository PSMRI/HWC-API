package com.iemr.hwc.repo.throatDiagnosis;

import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThroatDiagnosisAssessmentRepository 
        extends JpaRepository<ThroatDiagnosisAssessment, Long> {
}