package com.iemr.hwc.repo.couple;

import com.iemr.hwc.data.eligibleCouple.EligibleCoupleRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EligibleCoupleRegisterRepo extends JpaRepository<EligibleCoupleRegister, Long> {

    EligibleCoupleRegister findEligibleCoupleRegisterByBenId(Long benId);

    @Query(" SELECT ecr FROM EligibleCoupleRegister ecr WHERE ecr.createdBy = :userId and ecr.createdDate >= :fromDate and ecr.createdDate <= :toDate")
    List<EligibleCoupleRegister> getECRegRecords(@Param("userId") String userId,
                                                 @Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);
}
