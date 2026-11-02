package com.iemr.hwc.repo.painSymptomAssessment;

import com.iemr.hwc.data.painSymptom.PainSymptomData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface  PainSymptomAssessmentRepository extends JpaRepository<PainSymptomData,Long> {
    List<PainSymptomData> findByUserId(Integer userId);
}
