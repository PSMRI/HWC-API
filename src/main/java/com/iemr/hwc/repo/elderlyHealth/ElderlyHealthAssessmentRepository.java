package com.iemr.hwc.repo.elderlyHealth;

import com.iemr.hwc.data.elderlyHealth.ElderlyHealthAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElderlyHealthAssessmentRepository extends JpaRepository<ElderlyHealthAssessment, Integer> {


    List<ElderlyHealthAssessment> findByUserId(Integer userId);

    Optional<ElderlyHealthAssessment> findByPatientIDAndBenVisitNo(String patientId, Integer benVisitNo);
}
