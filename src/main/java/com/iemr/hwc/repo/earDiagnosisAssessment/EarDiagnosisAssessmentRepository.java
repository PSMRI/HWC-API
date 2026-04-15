package com.iemr.hwc.repo.earDiagnosisAssessment;

import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EarDiagnosisAssessmentRepository extends JpaRepository<EarDiagnosisAssessment, Integer> {

}