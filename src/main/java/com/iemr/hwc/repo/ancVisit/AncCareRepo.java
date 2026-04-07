package com.iemr.hwc.repo.ancVisit;

import com.iemr.hwc.data.anc.AncCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AncCareRepo extends JpaRepository<AncCare, Long> {
}
