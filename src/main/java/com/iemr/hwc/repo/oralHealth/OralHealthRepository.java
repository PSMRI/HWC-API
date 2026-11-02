package com.iemr.hwc.repo.oralHealth;

import com.iemr.hwc.data.oralHealth.OralHealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OralHealthRepository 
        extends JpaRepository<OralHealthData, Long> {
    List<OralHealthData> findByUserId(Integer userId);
}