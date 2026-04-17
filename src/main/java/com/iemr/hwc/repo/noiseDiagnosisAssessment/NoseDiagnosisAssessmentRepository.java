package com.iemr.hwc.repo.noiseDiagnosisAssessment;

import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoseDiagnosisAssessmentRepository 
        extends JpaRepository<NoseDiagnosisAssessment, Long> {

    List<NoseDiagnosisAssessment> findByPatientId(String patientId);

    List<NoseDiagnosisAssessment> findByPatientIdAndBenVisitNo(String patientId, Integer benVisitNo);
}