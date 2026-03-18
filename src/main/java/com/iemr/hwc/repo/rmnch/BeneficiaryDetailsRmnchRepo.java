package com.iemr.hwc.repo.rmnch;

import com.iemr.hwc.data.rmnch.RMNCHBeneficiaryDetailsRmnch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface BeneficiaryDetailsRmnchRepo extends JpaRepository<RMNCHBeneficiaryDetailsRmnch,Long> {
    RMNCHBeneficiaryDetailsRmnch findByBenRegId(BigInteger valueOf);
}
