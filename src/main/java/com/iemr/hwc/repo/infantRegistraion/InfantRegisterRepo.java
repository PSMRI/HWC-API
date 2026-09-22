package com.iemr.hwc.repo.infantRegistraion;

import com.iemr.hwc.data.infantRegistration.InfantRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface InfantRegisterRepo extends JpaRepository<InfantRegister, Long> {

    List<InfantRegister> findByCreatedByAndIsActive(String userName,Boolean isActive);

    InfantRegister findInfantRegisterByBenIdAndBabyIndexAndIsActive(Long benId, Integer babyIndex, Boolean isActive);
}
