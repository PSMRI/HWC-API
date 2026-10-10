package com.iemr.hwc.repo.childRegsitration;

import com.iemr.hwc.data.childRegistration.ChildRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ChildRegisterRepo extends JpaRepository<ChildRegister, Long> {

    @Query(" SELECT cr FROM ChildRegister cr WHERE cr.createdBy = :userId")
    List<ChildRegister> getChildDetailsForUser(@Param("userId") String userId);

//    @Query(value = "Select * from db_identity.i_cbacdetails where beneficiaryRegId = :benRegId and createdDate = :createdDate limit 1", nativeQuery = true)
    ChildRegister findChildRegisterByBenIdAndCreatedDate(Long benRegId, Timestamp createdDate);
}
