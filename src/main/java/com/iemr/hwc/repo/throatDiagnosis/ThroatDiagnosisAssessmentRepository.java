package com.iemr.hwc.repo.throatDiagnosis;

import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThroatDiagnosisAssessmentRepository 
        extends JpaRepository<ThroatDiagnosisAssessment, Integer> {

    List<ThroatDiagnosisAssessment> findByUserId(Integer userId);
}
