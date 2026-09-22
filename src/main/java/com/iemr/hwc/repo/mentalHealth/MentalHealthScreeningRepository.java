package com.iemr.hwc.repo.mentalHealth;

import com.iemr.hwc.data.mentalHealth.MentalHealthScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentalHealthScreeningRepository extends JpaRepository<MentalHealthScreening, Integer> {

    List<MentalHealthScreening> findByUserId(Integer userId);
}
