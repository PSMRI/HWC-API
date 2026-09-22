package com.iemr.hwc.repo.earDiagnosisAssessment;

import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EarDiagnosisAssessmentRepository extends JpaRepository<EarDiagnosisAssessment, Integer> {

    List<EarDiagnosisAssessment> findByUserId(Integer userId);
}