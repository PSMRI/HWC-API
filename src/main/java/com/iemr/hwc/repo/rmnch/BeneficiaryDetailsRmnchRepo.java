package com.iemr.hwc.repo.rmnch;

import com.iemr.hwc.data.rmnch.RMNCHBeneficiaryDetailsRmnch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface BeneficiaryDetailsRmnchRepo extends JpaRepository<RMNCHBeneficiaryDetailsRmnch,Long> {
    @Query("SELECT r FROM RMNCHBeneficiaryDetailsRmnch r WHERE r.BenRegId = :benRegId")
    RMNCHBeneficiaryDetailsRmnch findByBenRegId(@Param("benRegId") BigInteger benRegId);}
