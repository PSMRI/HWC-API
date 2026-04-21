package com.iemr.hwc.repo.elderlyHealth;

import com.iemr.hwc.data.elderlyHealth.ElderlyHealthAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElderlyHealthAssessmentRepository extends JpaRepository<ElderlyHealthAssessment, Integer> {

    Optional<ElderlyHealthAssessment> findByPatientIdAndBenVisitNo(String patientId, Integer benVisitNo);

    List<ElderlyHealthAssessment> findByUserId(Integer userId);
}
