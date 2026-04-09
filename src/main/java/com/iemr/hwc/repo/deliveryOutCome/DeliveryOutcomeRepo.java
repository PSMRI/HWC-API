package com.iemr.hwc.repo.deliveryOutCome;

import com.iemr.hwc.data.deliveryOutcome.DeliveryOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DeliveryOutcomeRepo extends JpaRepository<DeliveryOutcome, Long> {

    @Query(" SELECT do FROM DeliveryOutcome do WHERE do.createdBy = :userId and do.isActive = true")
    List<DeliveryOutcome> getDeliveryOutcomeByAshaId(@Param("userId") String userId);

    List<DeliveryOutcome> findByCreatedBy(String userName);

    DeliveryOutcome findDeliveryOutcomeByBenIdAndIsActive(Long benId, Boolean isActive);
}
