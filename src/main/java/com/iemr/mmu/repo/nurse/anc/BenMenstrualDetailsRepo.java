/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.BenMenstrualDetails;

@Repository
@RestResource(exported = false)
public interface BenMenstrualDetailsRepo extends CrudRepository<BenMenstrualDetails, Integer>{
	
	@Query("select Date(createdDate), regularity, cycleLength, bloodFlowDuration, problemName, Date(lMPDate) "
			+ "from BenMenstrualDetails a where a.beneficiaryRegID = :beneficiaryRegID AND menstrualCycleStatusID is not null AND deleted = false "
			+ "order by createdDate DESC")
	public ArrayList<Object[]> getBenMenstrualDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, menstrualCycleStatusID, menstrualCycleStatus, regularity, menstrualCyclelengthID, "
			+ "cycleLength, menstrualFlowDurationID, bloodFlowDuration, menstrualProblemID, problemName, lMPDate, visitCode FROM BenMenstrualDetails "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenMenstrualDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	

	@Query("SELECT processed from BenAnthropometryDetail where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenMenstrualDetailStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	
	@Transactional
	@Modifying
	@Query("update BenMenstrualDetails set menstrualCycleStatusID=:menstrualCycleStatusID, menstrualCycleStatus=:menstrualCycleStatus, "
			+ "regularity=:regularity,  menstrualCyclelengthID=:menstrualCyclelengthID, cycleLength=:cycleLength,"
			+ " bloodFlowDuration=:bloodFlowDuration, menstrualProblemID=:menstrualProblemID, problemName=:problemName, lMPDate=:lMPDate,"
			+ " menstrualFlowDurationID=:menstrualFlowDurationID,  modifiedBy=:modifiedBy, processed=:processed where "
			+ "beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode")
	public int updateMenstrualDetails(
			@Param("menstrualCycleStatusID") Short menstrualCycleStatusID,
			@Param("menstrualCycleStatus") String menstrualCycleStatus,
			@Param("regularity") String regularity,
			@Param("menstrualCyclelengthID") Short menstrualCyclelengthID,
			@Param("cycleLength") String cycleLength,
			@Param("menstrualFlowDurationID") Short menstrualFlowDurationID,
			@Param("bloodFlowDuration") String bloodFlowDuration,
			@Param("menstrualProblemID") String menstrualProblemID,
			@Param("problemName") String problemName,
			@Param("lMPDate") Timestamp lMPDate,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update BenMenstrualDetails set lMPDate=:lMPDate,modifiedBy=:modifiedBy"
			+ " where beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode AND deleted = false ")
	public int updateLmpDate(@Param("lMPDate") Timestamp lMPDate,
			@Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
	
}
